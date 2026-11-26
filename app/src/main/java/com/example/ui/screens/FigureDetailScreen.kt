package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material.icons.outlined.TextDecrease
import androidx.compose.material.icons.outlined.TextIncrease
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.audio.NarrationStatus
import com.example.audio.NarrationUiState
import com.example.data.model.FigureEntity
import com.example.ui.components.AntiqueDivider
import com.example.ui.components.GlassVitrineDisplay
import com.example.ui.components.MuseumStoneBackground
import com.example.ui.components.NarrationPlayerCard
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BoneParchment
import com.example.ui.theme.BurnishedGold
import com.example.ui.theme.CrimsonVelvet
import com.example.ui.theme.CryptShadow
import com.example.ui.theme.DarkBronzeGold
import com.example.ui.theme.MutedPewter
import com.example.ui.theme.NocturnalPlum
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.PlayfairFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.StoneBorder
import com.example.ui.theme.VelvetAmethyst
import com.example.ui.theme.WarmAlabaster

@Composable
fun FigureDetailScreen(
    figure: FigureEntity?,
    narrationState: NarrationUiState,
    onPrepareAudio: (FigureEntity) -> Unit,
    onPlayOrResumeAudio: () -> Unit,
    onPauseAudio: () -> Unit,
    onStopAudio: () -> Unit,
    onRestartAudio: () -> Unit,
    onAttachPhoto: (String, Uri) -> Unit,
    onRemovePhoto: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showReadingModal by remember { mutableStateOf(false) }
    var showAudioPlayer by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val currentId = figure?.id
        if (uri != null && currentId != null) {
            onAttachPhoto(currentId, uri)
        }
    }

    LaunchedEffect(figure?.id) {
        if (figure != null) {
            onPrepareAudio(figure)
        }
    }

    // Always stop audio cleanly when leaving this exhibition page
    DisposableEffect(Unit) {
        onDispose {
            onStopAudio()
        }
    }

    MuseumStoneBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top Museum Exhibition Bar
            Surface(
                color = ObsidianBlack.copy(alpha = 0.88f),
                border = BorderStroke(0.5.dp, StoneBorder)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onStopAudio()
                            onNavigateBack()
                        },
                        border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.6f)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("back_to_catalog_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Regresar al catálogo",
                            tint = BurnishedGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Catálogo",
                            style = MaterialTheme.typography.labelMedium,
                            color = BurnishedGold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SALA DE EXPOSICIÓN",
                            style = MaterialTheme.typography.labelSmall,
                            color = AntiqueGold
                        )
                        Text(
                            text = figure?.collectionName ?: "Cargando pieza...",
                            style = MaterialTheme.typography.titleMedium,
                            color = BoneParchment,
                            maxLines = 1
                        )
                    }
                }
            }

            if (figure == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = AntiqueGold)
                }
            } else {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val isWide = maxWidth >= 720.dp

                    if (isWide) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.45f)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                GlassVitrineDisplay(
                                    figure = figure,
                                    height = 420.dp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                VitrinePhotoActionBar(
                                    hasPhoto = !figure.photoAssetPath.isNullOrBlank(),
                                    onPickPhoto = {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                    onRemovePhoto = { onRemovePhoto(figure.id) }
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(0.55f)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                ExhibitionDetailsContent(
                                    figure = figure,
                                    showAudioPlayer = showAudioPlayer ||
                                        narrationState.status == NarrationStatus.PLAYING ||
                                        narrationState.status == NarrationStatus.PAUSED,
                                    narrationState = narrationState,
                                    onOpenReader = { showReadingModal = true },
                                    onActivateAudio = {
                                        showAudioPlayer = true
                                        onPlayOrResumeAudio()
                                    },
                                    onPlayOrResumeAudio = onPlayOrResumeAudio,
                                    onPauseAudio = onPauseAudio,
                                    onStopAudio = onStopAudio,
                                    onRestartAudio = onRestartAudio
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .widthIn(max = 620.dp)
                                .align(Alignment.TopCenter)
                        ) {
                            GlassVitrineDisplay(
                                figure = figure,
                                height = 320.dp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            VitrinePhotoActionBar(
                                hasPhoto = !figure.photoAssetPath.isNullOrBlank(),
                                onPickPhoto = {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                onRemovePhoto = { onRemovePhoto(figure.id) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            ExhibitionDetailsContent(
                                figure = figure,
                                showAudioPlayer = showAudioPlayer ||
                                    narrationState.status == NarrationStatus.PLAYING ||
                                    narrationState.status == NarrationStatus.PAUSED,
                                narrationState = narrationState,
                                onOpenReader = { showReadingModal = true },
                                onActivateAudio = {
                                    showAudioPlayer = true
                                    onPlayOrResumeAudio()
                                },
                                onPlayOrResumeAudio = onPlayOrResumeAudio,
                                onPauseAudio = onPauseAudio,
                                onStopAudio = onStopAudio,
                                onRestartAudio = onRestartAudio
                            )

                            Spacer(modifier = Modifier.height(28.dp))
                        }
                    }
                }
            }
        }
    }

    if (showReadingModal && figure != null) {
        MuseumReadingRoomDialog(
            figure = figure,
            onDismiss = { showReadingModal = false }
        )
    }
}

@Composable
private fun ExhibitionDetailsContent(
    figure: FigureEntity,
    showAudioPlayer: Boolean,
    narrationState: NarrationUiState,
    onOpenReader: () -> Unit,
    onActivateAudio: () -> Unit,
    onPlayOrResumeAudio: () -> Unit,
    onPauseAudio: () -> Unit,
    onStopAudio: () -> Unit,
    onRestartAudio: () -> Unit
) {
    // Inscribed Museum Plaque
    Surface(
        color = NocturnalPlum.copy(alpha = 0.90f),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "FICHA ${figure.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = AntiqueGold
                )
                VerificationBadge(statusCode = figure.verificationStatus)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = figure.name,
                style = MaterialTheme.typography.displayMedium,
                color = BoneParchment
            )

            Spacer(modifier = Modifier.height(10.dp))
            AntiqueDivider()
            Spacer(modifier = Modifier.height(10.dp))

            MetadataRow(label = "Año de lanzamiento", value = figure.yearLabel)
            Spacer(modifier = Modifier.height(6.dp))
            MetadataRow(label = "Colección", value = figure.collectionName)
            if (figure.subGroup.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                MetadataRow(label = "Clasificación", value = figure.subGroup)
            }
            Spacer(modifier = Modifier.height(6.dp))
            MetadataRow(label = "Época", value = figure.era)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Primary Museum Buttons: LEER HISTORIA & ESCUCHAR HISTORIA
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onOpenReader,
            colors = ButtonDefaults.buttonColors(
                containerColor = AntiqueGold,
                contentColor = ObsidianBlack
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .minimumInteractiveComponentSize()
                .testTag("read_history_button")
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "LEER HISTORIA",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onActivateAudio,
            colors = ButtonDefaults.buttonColors(
                containerColor = CrimsonVelvet,
                contentColor = BoneParchment
            ),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.7f)),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .minimumInteractiveComponentSize()
                .testTag("listen_history_button")
        ) {
            Icon(
                imageVector = Icons.Outlined.RecordVoiceOver,
                contentDescription = null,
                tint = BurnishedGold,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ESCUCHAR HISTORIA",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
                color = BurnishedGold,
                fontWeight = FontWeight.Bold
            )
        }
    }

    AnimatedVisibility(visible = showAudioPlayer) {
        Column {
            Spacer(modifier = Modifier.height(14.dp))
            NarrationPlayerCard(
                narrationState = narrationState,
                onPlayOrResume = onPlayOrResumeAudio,
                onPause = onPauseAudio,
                onStop = onStopAudio,
                onRestart = onRestartAudio
            )
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Full History Plaque
    val historyTitle = if (figure.verificationStatus != com.example.data.model.VerificationStatus.CONFIRMED.code) {
        "HISTORIA • Historia pendiente de documentación"
    } else {
        "HISTORIA COMPLETA"
    }
    MuseumSectionCard(
        title = historyTitle,
        body = figure.history
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Documented Curiosities Plaque
    MuseumSectionCard(
        title = "CURIOSIDADES DOCUMENTADAS",
        body = figure.curiosities
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Archival Source & Authenticity Note
    Surface(
        color = CryptShadow.copy(alpha = 0.90f),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, StoneBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "FUENTES Y REGISTRO DE AUTENTICIDAD",
                style = MaterialTheme.typography.labelLarge,
                color = MutedPewter
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = figure.sourceNote,
                style = MaterialTheme.typography.bodyMedium,
                color = WarmAlabaster.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun MetadataRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedPewter
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = BurnishedGold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun MuseumSectionCard(
    title: String,
    body: String
) {
    Surface(
        color = NocturnalPlum.copy(alpha = 0.88f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, StoneBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = BurnishedGold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = PlayfairFontFamily,
                    lineHeight = 27.sp
                ),
                color = BoneParchment
            )
        }
    }
}

@Composable
private fun MuseumReadingRoomDialog(
    figure: FigureEntity,
    onDismiss: () -> Unit
) {
    var fontScale by remember { mutableFloatStateOf(1.0f) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            color = ObsidianBlack,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .testTag("reading_modal")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Reading Room Header
                Surface(
                    color = NocturnalPlum,
                    border = BorderStroke(0.5.dp, DarkBronzeGold.copy(alpha = 0.5f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.AutoStories,
                                contentDescription = null,
                                tint = AntiqueGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SALA DE LECTURA",
                                style = MaterialTheme.typography.labelLarge,
                                color = BurnishedGold
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { fontScale = (fontScale - 0.1f).coerceAtLeast(0.85f) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.TextDecrease,
                                    contentDescription = "Reducir tamaño de letra",
                                    tint = WarmAlabaster
                                )
                            }
                            IconButton(
                                onClick = { fontScale = (fontScale + 0.1f).coerceAtMost(1.4f) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.TextIncrease,
                                    contentDescription = "Aumentar tamaño de letra",
                                    tint = WarmAlabaster
                                )
                            }
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("close_reading_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Cerrar lectura",
                                    tint = BurnishedGold
                                )
                            }
                        }
                    }
                }

                // Comfortable Long-Form Spanish Reading Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                        .widthIn(max = 640.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "${figure.collectionName} • ${figure.yearLabel}",
                        style = MaterialTheme.typography.labelLarge,
                        color = AntiqueGold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = figure.name,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = PlayfairFontFamily
                        ),
                        color = BoneParchment
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    AntiqueDivider()
                    Spacer(modifier = Modifier.height(20.dp))

                    if (figure.verificationStatus != com.example.data.model.VerificationStatus.CONFIRMED.code) {
                        Surface(
                            color = VelvetAmethyst.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.55f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Historia pendiente de documentación",
                                style = MaterialTheme.typography.labelLarge,
                                color = BurnishedGold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    val paragraphs = remember(figure.history) {
                        figure.history.split("\n").filter { it.isNotBlank() }
                    }

                    paragraphs.forEach { paragraph ->
                        Text(
                            text = paragraph,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = PlayfairFontFamily,
                                fontSize = (18f * fontScale).sp,
                                lineHeight = (29f * fontScale).sp
                            ),
                            color = BoneParchment.copy(alpha = 0.95f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (figure.curiosities.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = VelvetAmethyst.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.45f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "CURIOSIDADES DOCUMENTADAS",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = BurnishedGold
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = figure.curiosities,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontFamily = PlusJakartaFontFamily,
                                        fontSize = (15f * fontScale).sp,
                                        lineHeight = (24f * fontScale).sp
                                    ),
                                    color = WarmAlabaster
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = figure.sourceNote,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedPewter
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun VitrinePhotoActionBar(
    hasPhoto: Boolean,
    onPickPhoto: () -> Unit,
    onRemovePhoto: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onPickPhoto,
            border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.7f)),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
            modifier = Modifier
                .weight(1f)
                .minimumInteractiveComponentSize()
                .testTag("pick_photo_button")
        ) {
            Icon(
                imageVector = Icons.Outlined.AddPhotoAlternate,
                contentDescription = null,
                tint = BurnishedGold,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (hasPhoto) "CAMBIAR FOTOGRAFÍA" else "AÑADIR FOTOGRAFÍA",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                color = BurnishedGold,
                fontWeight = FontWeight.Bold
            )
        }

        if (hasPhoto) {
            OutlinedButton(
                onClick = onRemovePhoto,
                border = BorderStroke(1.dp, StoneBorder),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("remove_photo_button")
            ) {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    contentDescription = "Quitar fotografía",
                    tint = WarmAlabaster,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
