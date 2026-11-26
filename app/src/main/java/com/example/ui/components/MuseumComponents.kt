package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.R
import com.example.audio.NarrationStatus
import com.example.audio.NarrationUiState
import com.example.data.model.FigureEntity
import com.example.data.model.VerificationStatus
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BoneParchment
import com.example.ui.theme.BurnishedGold
import com.example.ui.theme.ConfirmedEmerald
import com.example.ui.theme.ConfirmedEmeraldBg
import com.example.ui.theme.CrimsonVelvet
import com.example.ui.theme.CryptShadow
import com.example.ui.theme.DarkBronzeGold
import com.example.ui.theme.DeepGarnet
import com.example.ui.theme.MistViolet
import com.example.ui.theme.MutedPewter
import com.example.ui.theme.NocturnalPlum
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.PartialAmber
import com.example.ui.theme.PartialAmberBg
import com.example.ui.theme.PendingSilver
import com.example.ui.theme.PendingSilverBg
import com.example.ui.theme.StoneBorder
import com.example.ui.theme.VelvetAmethyst
import com.example.ui.theme.WarmAlabaster
import com.example.ui.theme.WineRed
import kotlin.math.sin

@Composable
fun MuseumStoneBackground(
    modifier: Modifier = Modifier,
    enableMist: Boolean = true,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mist_anim")
    val mistPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mist_phase"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_museum_stone),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.28f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ObsidianBlack.copy(alpha = 0.85f),
                            NocturnalPlum.copy(alpha = 0.55f),
                            ObsidianBlack.copy(alpha = 0.94f)
                        )
                    )
                )
        )

        if (enableMist) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val driftX = sin(mistPhase) * w * 0.08f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MistViolet.copy(alpha = 0.22f),
                            Color.Transparent
                        ),
                        center = Offset(w * 0.25f + driftX, h * 0.22f),
                        radius = w * 0.65f
                    ),
                    center = Offset(w * 0.25f + driftX, h * 0.22f),
                    radius = w * 0.65f
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            WineRed.copy(alpha = 0.14f),
                            Color.Transparent
                        ),
                        center = Offset(w * 0.78f - driftX, h * 0.78f),
                        radius = w * 0.60f
                    ),
                    center = Offset(w * 0.78f - driftX, h * 0.78f),
                    radius = w * 0.60f
                )
            }
        }

        content()
    }
}

@Composable
fun VerificationBadge(
    statusCode: String,
    compact: Boolean = false,
    modifier: Modifier = Modifier
) {
    val status = VerificationStatus.fromCode(statusCode)
    val (bg, fg, icon) = when (status) {
        VerificationStatus.CONFIRMED -> Triple(ConfirmedEmeraldBg, ConfirmedEmerald, Icons.Outlined.Verified)
        VerificationStatus.PARTIAL -> Triple(PartialAmberBg, PartialAmber, Icons.Outlined.Info)
        VerificationStatus.PENDING_ARCHIVE -> Triple(PendingSilverBg, PendingSilver, Icons.Outlined.HourglassEmpty)
    }

    Surface(
        color = bg.copy(alpha = 0.92f),
        contentColor = fg,
        shape = RoundedCornerShape(999.dp),
        border = BorderStroke(1.dp, fg.copy(alpha = 0.45f)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = if (compact) 8.dp else 10.dp,
                vertical = if (compact) 3.dp else 5.dp
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = status.label,
                modifier = Modifier.size(if (compact) 12.dp else 14.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = if (compact) {
                    when (status) {
                        VerificationStatus.CONFIRMED -> "Confirmada"
                        VerificationStatus.PARTIAL -> "Parcial"
                        VerificationStatus.PENDING_ARCHIVE -> "Pendiente"
                    }
                } else {
                    status.label
                },
                style = MaterialTheme.typography.labelSmall,
                color = fg,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GlassVitrineDisplay(
    figure: FigureEntity,
    height: Dp = 320.dp,
    compact: Boolean = false,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "spotlight")
    val glowAlpha by transition.animateFloat(
        initialValue = 0.24f,
        targetValue = 0.42f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "spotlight_alpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(if (compact) 14.dp else 20.dp))
            .border(
                width = if (compact) 1.dp else 1.5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AntiqueGold.copy(alpha = 0.75f),
                        DarkBronzeGold.copy(alpha = 0.35f),
                        AntiqueGold.copy(alpha = 0.65f)
                    )
                ),
                shape = RoundedCornerShape(if (compact) 14.dp else 20.dp)
            )
            .background(CryptShadow)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_vitrine_pedestal),
            contentDescription = "Vitrina de exposición de ${figure.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (compact) 0.78f else 0.92f)
        )

        // Overhead museum spotlight cone & subtle mist
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cone = Path().apply {
                moveTo(w * 0.36f, 0f)
                lineTo(w * 0.64f, 0f)
                lineTo(w * 0.88f, h * 0.82f)
                lineTo(w * 0.12f, h * 0.82f)
                close()
            }
            drawPath(
                path = cone,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BurnishedGold.copy(alpha = glowAlpha),
                        AntiqueGold.copy(alpha = 0.08f),
                        Color.Transparent
                    )
                )
            )

            // Subtle glass reflection sheen
            drawLine(
                color = Color.White.copy(alpha = 0.10f),
                start = Offset(w * 0.08f, h * 0.06f),
                end = Offset(w * 0.28f, h * 0.72f),
                strokeWidth = if (compact) 6f else 12f,
                cap = StrokeCap.Round
            )
        }

        val existingPhotoFile = figure.photoAssetPath
            ?.takeIf { it.isNotBlank() }
            ?.let { java.io.File(it) }
            ?.takeIf { it.exists() && it.length() > 0L }

        if (existingPhotoFile != null) {
            val context = androidx.compose.ui.platform.LocalContext.current
            AsyncImage(
                model = coil.request.ImageRequest.Builder(context)
                    .data(existingPhotoFile)
                    .memoryCacheKey("${figure.id}_${existingPhotoFile.lastModified()}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Fotografía de ${figure.name}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (compact) 10.dp else 20.dp)
            )
        } else {
            // Provisional museum illustration & plaque when no photo has been added yet
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = if (compact) 10.dp else 28.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = ObsidianBlack.copy(alpha = 0.72f),
                    border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.55f)),
                    modifier = Modifier.size(if (compact) 42.dp else 64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = "Fotografía pendiente",
                            tint = AntiqueGold,
                            modifier = Modifier.size(if (compact) 20.dp else 30.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(if (compact) 6.dp else 12.dp))
                Surface(
                    color = ObsidianBlack.copy(alpha = 0.80f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.5f))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(
                            horizontal = if (compact) 8.dp else 14.dp,
                            vertical = if (compact) 4.dp else 8.dp
                        )
                    ) {
                        Text(
                            text = if (compact) "Fotografía pendiente" else "FOTOGRAFÍA PENDIENTE",
                            style = if (compact) {
                                MaterialTheme.typography.labelSmall
                            } else {
                                MaterialTheme.typography.labelLarge
                            },
                            color = BurnishedGold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (!compact) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pieza ${figure.id} • Vitrina lista para añadir fotografía",
                                style = MaterialTheme.typography.labelSmall,
                                color = WarmAlabaster.copy(alpha = 0.85f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // Top corner specimen catalog tag
        Surface(
            color = ObsidianBlack.copy(alpha = 0.78f),
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.45f)),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(if (compact) 6.dp else 12.dp)
        ) {
            Text(
                text = figure.yearLabel,
                style = MaterialTheme.typography.labelSmall,
                color = BurnishedGold,
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
fun NarrationPlayerCard(
    narrationState: NarrationUiState,
    onPlayOrResume: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = NocturnalPlum.copy(alpha = 0.94f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.55f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.VolumeUp,
                        contentDescription = "Audio guía del museo",
                        tint = AntiqueGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AUDIO GUÍA DEL MUSEO",
                        style = MaterialTheme.typography.labelLarge,
                        color = BurnishedGold
                    )
                }
                if (narrationState.totalSegments > 0) {
                    Text(
                        text = "${(narrationState.currentSegmentIndex + 1).coerceAtMost(narrationState.totalSegments)}/${narrationState.totalSegments}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MutedPewter
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = narrationState.statusMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = WarmAlabaster
            )

            AnimatedVisibility(visible = narrationState.status == NarrationStatus.PLAYING || narrationState.status == NarrationStatus.PAUSED) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { narrationState.progressFraction.coerceIn(0f, 1f) },
                        color = AntiqueGold,
                        trackColor = VelvetAmethyst,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(999.dp))
                    )
                    if (narrationState.currentSegmentText.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“${narrationState.currentSegmentText}”",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BoneParchment.copy(alpha = 0.85f),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                when (narrationState.status) {
                    NarrationStatus.PLAYING -> {
                        Button(
                            onClick = onPause,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AntiqueGold,
                                contentColor = ObsidianBlack
                            ),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("audio_pause_button")
                        ) {
                            Icon(Icons.Outlined.Pause, contentDescription = "Pausar narración", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pausar", fontWeight = FontWeight.SemiBold)
                        }
                    }

                    NarrationStatus.PAUSED -> {
                        Button(
                            onClick = onPlayOrResume,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AntiqueGold,
                                contentColor = ObsidianBlack
                            ),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("audio_resume_button")
                        ) {
                            Icon(Icons.Outlined.PlayArrow, contentDescription = "Continuar narración", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Continuar", fontWeight = FontWeight.SemiBold)
                        }
                    }

                    else -> {
                        Button(
                            onClick = onPlayOrResume,
                            enabled = narrationState.status != NarrationStatus.UNAVAILABLE,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CrimsonVelvet,
                                contentColor = BoneParchment
                            ),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("audio_play_button")
                        ) {
                            Icon(Icons.Outlined.PlayArrow, contentDescription = "Reproducir narración", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Reproducir", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                OutlinedButton(
                    onClick = onStop,
                    enabled = narrationState.status == NarrationStatus.PLAYING || narrationState.status == NarrationStatus.PAUSED,
                    border = BorderStroke(1.dp, StoneBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WarmAlabaster),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("audio_stop_button")
                ) {
                    Icon(Icons.Outlined.Stop, contentDescription = "Detener narración", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Detener")
                }

                OutlinedButton(
                    onClick = onRestart,
                    enabled = narrationState.status != NarrationStatus.UNAVAILABLE,
                    border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.7f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BurnishedGold),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("audio_restart_button")
                ) {
                    Icon(Icons.Outlined.Replay, contentDescription = "Reiniciar narración", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reiniciar")
                }
            }
        }
    }
}

@Composable
fun AntiqueDivider(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, AntiqueGold.copy(alpha = 0.65f))
                    )
                )
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(6.dp)
                .background(AntiqueGold, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(AntiqueGold.copy(alpha = 0.65f), Color.Transparent)
                    )
                )
        )
    }
}
