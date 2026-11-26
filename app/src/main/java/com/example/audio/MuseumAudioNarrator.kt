package com.example.audio

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import java.util.UUID

enum class NarrationStatus {
    IDLE,
    PLAYING,
    PAUSED,
    COMPLETED,
    UNAVAILABLE
}

data class NarrationUiState(
    val figureId: String? = null,
    val status: NarrationStatus = NarrationStatus.IDLE,
    val isReady: Boolean = false,
    val currentSegmentIndex: Int = 0,
    val totalSegments: Int = 0,
    val currentSegmentText: String = "",
    val statusMessage: String = "Narrador de museo listo para reproducir sin conexión."
) {
    val progressFraction: Float
        get() = if (totalSegments <= 0) 0f else (currentSegmentIndex + 1).toFloat() / totalSegments.toFloat()
}

class MuseumAudioNarrator(context: Context) : TextToSpeech.OnInitListener {

    private val appContext = context.applicationContext
    private var tts: TextToSpeech? = null
    private var segments: List<String> = emptyList()
    private var activeSessionToken: String = ""
    private var isEngineInitialized = false
    private var hasSpanishVoice = false
    private var pendingStartAfterInit = false

    private val _state = MutableStateFlow(NarrationUiState())
    val state: StateFlow<NarrationUiState> = _state.asStateFlow()

    init {
        try {
            tts = TextToSpeech(appContext, this)
        } catch (_: Throwable) {
            _state.update {
                it.copy(
                    status = NarrationStatus.UNAVAILABLE,
                    isReady = false,
                    statusMessage = "El motor de síntesis de voz no está disponible en este dispositivo."
                )
            }
        }
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) {
            isEngineInitialized = false
            _state.update {
                it.copy(
                    status = NarrationStatus.UNAVAILABLE,
                    isReady = false,
                    statusMessage = "No se pudo iniciar la narración de voz sin conexión."
                )
            }
            return
        }

        val engine = tts ?: return
        val preferredLocales = listOf(
            Locale("es", "CO"),
            Locale("es", "ES"),
            Locale("es", "MX"),
            Locale("es")
        )
        var selectedResult = TextToSpeech.LANG_NOT_SUPPORTED
        for (locale in preferredLocales) {
            val result = engine.isLanguageAvailable(locale)
            if (result >= TextToSpeech.LANG_AVAILABLE) {
                engine.language = locale
                selectedResult = result
                break
            }
        }

        hasSpanishVoice = selectedResult >= TextToSpeech.LANG_AVAILABLE
        isEngineInitialized = true
        engine.setSpeechRate(0.94f)
        engine.setPitch(0.98f)
        engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) = Unit

            override fun onDone(utteranceId: String?) {
                handleUtteranceCompleted(utteranceId)
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                handleUtteranceError(utteranceId)
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                handleUtteranceError(utteranceId)
            }
        })

        if (!hasSpanishVoice) {
            _state.update {
                it.copy(
                    status = NarrationStatus.UNAVAILABLE,
                    isReady = false,
                    statusMessage = "No hay una voz en español instalada en el dispositivo para narrar sin conexión."
                )
            }
            return
        }

        _state.update {
            it.copy(
                isReady = true,
                status = if (it.status == NarrationStatus.UNAVAILABLE) NarrationStatus.IDLE else it.status,
                statusMessage = "Voz en español lista para narrar sin conexión."
            )
        }

        if (pendingStartAfterInit && segments.isNotEmpty()) {
            pendingStartAfterInit = false
            speakFromIndex(_state.value.currentSegmentIndex)
        }
    }

    fun prepare(figureId: String, fullNarrationText: String) {
        if (_state.value.figureId == figureId && segments.isNotEmpty()) return
        stopInternal(resetFigure = false)
        val parsed = splitIntoNarrationSegments(fullNarrationText)
        segments = parsed
        _state.update {
            NarrationUiState(
                figureId = figureId,
                status = if (!isEngineInitialized || hasSpanishVoice) NarrationStatus.IDLE else NarrationStatus.UNAVAILABLE,
                isReady = isEngineInitialized && hasSpanishVoice,
                currentSegmentIndex = 0,
                totalSegments = parsed.size,
                currentSegmentText = parsed.firstOrNull().orEmpty(),
                statusMessage = if (isEngineInitialized && !hasSpanishVoice) {
                    "No hay una voz en español instalada en el dispositivo."
                } else {
                    "Audio guía lista para reproducir."
                }
            )
        }
    }

    fun playOrResume() {
        if (segments.isEmpty()) return
        if (!isEngineInitialized) {
            pendingStartAfterInit = true
            _state.update {
                it.copy(
                    status = NarrationStatus.PLAYING,
                    statusMessage = "Preparando voz en español..."
                )
            }
            return
        }
        if (!hasSpanishVoice) {
            _state.update {
                it.copy(
                    status = NarrationStatus.UNAVAILABLE,
                    statusMessage = "Instala una voz en español en Ajustes del sistema para usar la narración sin internet."
                )
            }
            return
        }

        val currentIdx = when (_state.value.status) {
            NarrationStatus.COMPLETED -> 0
            NarrationStatus.PAUSED -> _state.value.currentSegmentIndex.coerceIn(0, segments.lastIndex)
            NarrationStatus.IDLE -> 0
            NarrationStatus.PLAYING -> return
            NarrationStatus.UNAVAILABLE -> return
        }
        speakFromIndex(currentIdx)
    }

    fun pause() {
        if (_state.value.status != NarrationStatus.PLAYING) return
        activeSessionToken = UUID.randomUUID().toString()
        tts?.stop()
        _state.update {
            it.copy(
                status = NarrationStatus.PAUSED,
                statusMessage = "Narración en pausa (fragmento ${it.currentSegmentIndex + 1} de ${it.totalSegments})."
            )
        }
    }

    fun resume() {
        if (_state.value.status == NarrationStatus.PAUSED) {
            playOrResume()
        }
    }

    fun stop() {
        stopInternal(resetFigure = false)
    }

    fun restart() {
        if (segments.isEmpty()) return
        activeSessionToken = UUID.randomUUID().toString()
        tts?.stop()
        _state.update {
            it.copy(
                currentSegmentIndex = 0,
                currentSegmentText = segments.firstOrNull().orEmpty(),
                status = NarrationStatus.IDLE
            )
        }
        playOrResume()
    }

    fun shutdown() {
        activeSessionToken = UUID.randomUUID().toString()
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

    private fun stopInternal(resetFigure: Boolean) {
        pendingStartAfterInit = false
        activeSessionToken = UUID.randomUUID().toString()
        tts?.stop()
        _state.update { current ->
            NarrationUiState(
                figureId = if (resetFigure) null else current.figureId,
                status = if (isEngineInitialized && !hasSpanishVoice) NarrationStatus.UNAVAILABLE else NarrationStatus.IDLE,
                isReady = isEngineInitialized && hasSpanishVoice,
                currentSegmentIndex = 0,
                totalSegments = if (resetFigure) 0 else segments.size,
                currentSegmentText = if (resetFigure) "" else segments.firstOrNull().orEmpty(),
                statusMessage = if (isEngineInitialized && !hasSpanishVoice) {
                    "No hay una voz en español instalada en el dispositivo."
                } else {
                    "Narración detenida."
                }
            )
        }
    }

    private fun speakFromIndex(index: Int) {
        val engine = tts ?: return
        if (index !in segments.indices) {
            _state.update {
                it.copy(
                    status = NarrationStatus.COMPLETED,
                    statusMessage = "Narración completada."
                )
            }
            return
        }

        val sessionToken = UUID.randomUUID().toString()
        activeSessionToken = sessionToken
        val utteranceId = "$sessionToken::$index"

        _state.update {
            it.copy(
                status = NarrationStatus.PLAYING,
                currentSegmentIndex = index,
                currentSegmentText = segments[index],
                statusMessage = "Narrando fragmento ${index + 1} de ${segments.size}..."
            )
        }

        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }
        engine.speak(segments[index], TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }

    private fun handleUtteranceCompleted(utteranceId: String?) {
        if (utteranceId == null) return
        val parts = utteranceId.split("::")
        if (parts.size != 2) return
        val token = parts[0]
        val index = parts[1].toIntOrNull() ?: return
        if (token != activeSessionToken) return

        val nextIndex = index + 1
        if (nextIndex < segments.size) {
            speakFromIndex(nextIndex)
        } else {
            _state.update {
                it.copy(
                    status = NarrationStatus.COMPLETED,
                    currentSegmentIndex = segments.lastIndex.coerceAtLeast(0),
                    statusMessage = "Narración completada."
                )
            }
        }
    }

    private fun handleUtteranceError(utteranceId: String?) {
        if (utteranceId == null) return
        val token = utteranceId.substringBefore("::")
        if (token != activeSessionToken) return
        _state.update {
            it.copy(
                status = NarrationStatus.PAUSED,
                statusMessage = "No se pudo continuar la reproducción en este dispositivo."
            )
        }
    }

    internal companion object {
        fun splitIntoNarrationSegments(text: String): List<String> {
            val cleaned = text.replace("•", ". ")
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }

            val result = mutableListOf<String>()
            for (paragraph in cleaned) {
                val sentences = paragraph.split(Regex("(?<=[.!?])\\s+"))
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                if (sentences.isEmpty()) {
                    result += paragraph
                } else {
                    result += sentences
                }
            }
            return result
        }
    }
}
