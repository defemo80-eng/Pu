package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.MuseumAudioNarrator
import com.example.audio.NarrationUiState
import com.example.data.local.DracuversusDatabase
import com.example.data.model.EraPeriod
import com.example.data.model.FigureEntity
import com.example.data.model.VerificationStatus
import com.example.data.repository.MuseumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CatalogUiState(
    val isLoading: Boolean = true,
    val allFigures: List<FigureEntity> = emptyList(),
    val filteredFigures: List<FigureEntity> = emptyList(),
    val selectedEra: EraPeriod? = null,
    val selectedYear: Int? = null,
    val searchQuery: String = "",
    val availableYears: List<Int> = emptyList(),
    val totalCount: Int = 0,
    val confirmedCount: Int = 0,
    val partialCount: Int = 0,
    val pendingCount: Int = 0
)

class MuseumViewModel(application: Application) : AndroidViewModel(application) {

    private val database = DracuversusDatabase.getInstance(application)
    private val repository = MuseumRepository(database.figureDao())
    private val narrator = MuseumAudioNarrator(application)

    private val selectedEra = MutableStateFlow<EraPeriod?>(null)
    private val selectedYear = MutableStateFlow<Int?>(null)
    private val searchQuery = MutableStateFlow("")

    val narrationState: StateFlow<NarrationUiState> = narrator.state

    val catalogState: StateFlow<CatalogUiState> = combine(
        repository.observeAllFigures(),
        selectedEra,
        selectedYear,
        searchQuery
    ) { figures, era, year, query ->
        val yearsInScope = figures
            .filter { era == null || it.era == era.code }
            .map { it.year }
            .distinct()
            .sorted()

        val effectiveYear = if (year != null && year in yearsInScope) year else null
        val trimmedQuery = query.trim()

        val filtered = figures.filter { figure ->
            val matchesEra = era == null || figure.era == era.code
            val matchesYear = effectiveYear == null || figure.year == effectiveYear
            val matchesQuery = trimmedQuery.isEmpty() ||
                figure.name.contains(trimmedQuery, ignoreCase = true) ||
                figure.collectionName.contains(trimmedQuery, ignoreCase = true) ||
                figure.subGroup.contains(trimmedQuery, ignoreCase = true) ||
                figure.yearLabel.contains(trimmedQuery, ignoreCase = true)
            matchesEra && matchesYear && matchesQuery
        }

        CatalogUiState(
            isLoading = false,
            allFigures = figures,
            filteredFigures = filtered,
            selectedEra = era,
            selectedYear = effectiveYear,
            searchQuery = query,
            availableYears = yearsInScope,
            totalCount = figures.size,
            confirmedCount = figures.count { it.verificationStatus == VerificationStatus.CONFIRMED.code },
            partialCount = figures.count { it.verificationStatus == VerificationStatus.PARTIAL.code },
            pendingCount = figures.count { it.verificationStatus == VerificationStatus.PENDING_ARCHIVE.code }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CatalogUiState(isLoading = true)
    )

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    fun selectEra(era: EraPeriod?) {
        selectedEra.value = era
        if (era != null && selectedYear.value != null) {
            val y = selectedYear.value!!
            if (y !in era.startYear..era.endYear) {
                selectedYear.value = null
            }
        }
    }

    fun selectYear(year: Int?) {
        selectedYear.value = if (selectedYear.value == year) null else year
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun clearFilters() {
        selectedEra.value = null
        selectedYear.value = null
        searchQuery.value = ""
    }

    fun observeFigureById(figureId: String): Flow<FigureEntity?> =
        repository.observeFigureById(figureId)

    fun prepareAudioForFigure(figure: FigureEntity) {
        val fullNarration = buildString {
            append("${figure.name}. ")
            append("Colección: ${figure.collectionName}. ")
            append("Año de lanzamiento: ${figure.yearLabel}. ")
            if (figure.subGroup.isNotBlank()) {
                append("Clasificación: ${figure.subGroup}. ")
            }
            append("\n")
            append(figure.history)
            if (figure.curiosities.isNotBlank()) {
                append("\nCuriosidades documentadas. ")
                append(figure.curiosities)
            }
        }
        narrator.prepare(figure.id, fullNarration)
    }

    fun playOrResumeAudio() = narrator.playOrResume()

    fun pauseAudio() = narrator.pause()

    fun resumeAudio() = narrator.resume()

    fun stopAudio() = narrator.stop()

    fun restartAudio() = narrator.restart()

    fun importPhotoFromUri(figureId: String, sourceUri: android.net.Uri) {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            val app = getApplication<Application>()
            val photosDir = java.io.File(app.filesDir, "figure_photos").apply { mkdirs() }
            val safeId = figureId.replace(Regex("[^A-Za-z0-9_-]"), "_")
            val targetFile = java.io.File(photosDir, "${safeId}_${System.currentTimeMillis()}.jpg")
            try {
                app.contentResolver.openInputStream(sourceUri)?.use { input ->
                    targetFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                if (targetFile.exists() && targetFile.length() > 0L) {
                    // Delete any older photo files belonging exclusively to this figureId
                    photosDir.listFiles()?.forEach { existing ->
                        if (existing.name.startsWith("${safeId}_") && existing.absolutePath != targetFile.absolutePath) {
                            existing.delete()
                        }
                    }
                    repository.updateFigurePhoto(figureId, targetFile.absolutePath)
                }
            } catch (_: Throwable) {
                if (targetFile.exists()) {
                    targetFile.delete()
                }
            }
        }
    }

    fun removeFigurePhoto(figureId: String) {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            val app = getApplication<Application>()
            val photosDir = java.io.File(app.filesDir, "figure_photos")
            val safeId = figureId.replace(Regex("[^A-Za-z0-9_-]"), "_")
            photosDir.listFiles()?.forEach { existing ->
                if (existing.name.startsWith("${safeId}_") || existing.name == "$figureId.jpg") {
                    existing.delete()
                }
            }
            repository.updateFigurePhoto(figureId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        narrator.shutdown()
    }
}
