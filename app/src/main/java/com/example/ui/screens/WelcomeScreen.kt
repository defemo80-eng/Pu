package com.example.ui.screens

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.AntiqueDivider
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BoneParchment
import com.example.ui.theme.BurnishedGold
import com.example.ui.theme.CrimsonVelvet
import com.example.ui.theme.DarkBronzeGold
import com.example.ui.theme.MistViolet
import com.example.ui.theme.NocturnalPlum
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.WarmAlabaster
import kotlin.math.sin

@Composable
fun WelcomeScreen(
    onEnterMuseum: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "welcome_atmosphere")
    val mistShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mist_shift"
    )
    val moonGlow by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "moon_glow"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // User-provided Dracuversus cover artwork (castle, illuminated moon, and characters)
        Image(
            painter = painterResource(id = R.drawable.img_cover_dracuversus),
            contentDescription = "Portada oficial de Dracuversus con el castillo, la luna iluminada y sus personajes",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.95f)
        )

        // Subtle atmospheric moonlight & drifting mist overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val drift = sin(mistShift) * w * 0.08f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        BurnishedGold.copy(alpha = moonGlow * 0.65f),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.36f, h * 0.16f),
                    radius = w * 0.45f
                ),
                center = Offset(w * 0.36f, h * 0.16f),
                radius = w * 0.45f
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MistViolet.copy(alpha = 0.25f),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.3f + drift, h * 0.78f),
                    radius = w * 0.65f
                ),
                center = Offset(w * 0.3f + drift, h * 0.78f),
                radius = w * 0.65f
            )
        }

        // Deep obsidian museum vignette focused at top & bottom to keep artwork bright in the center
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to ObsidianBlack.copy(alpha = 0.72f),
                        0.22f to ObsidianBlack.copy(alpha = 0.12f),
                        0.58f to Color.Transparent,
                        0.76f to NocturnalPlum.copy(alpha = 0.74f),
                        1.0f to ObsidianBlack.copy(alpha = 0.96f)
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 28.dp)
                .widthIn(max = 560.dp)
                .align(Alignment.Center)
        ) {
            // Top Museum Crest & Logo (compact to showcase castle and moon clearly)
            androidx.compose.foundation.layout.Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = ObsidianBlack.copy(alpha = 0.85f),
                    border = BorderStroke(1.5.dp, AntiqueGold),
                    modifier = Modifier.size(52.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_launcher_crest),
                        contentDescription = "Emblema oficial de Dracuversus",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))

                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "MUSEO Y ENCICLOPEDIA",
                        style = MaterialTheme.typography.labelMedium,
                        color = BurnishedGold,
                        letterSpacing = 2.0.sp
                    )
                    Text(
                        text = "DRACUVERSUS",
                        style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp),
                        color = BoneParchment
                    )
                }
            }

            // Bottom Museum Plaque & Entrance CTA
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = ObsidianBlack.copy(alpha = 0.78f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(
                        1.dp,
                        Brush.linearGradient(
                            colors = listOf(AntiqueGold, DarkBronzeGold, AntiqueGold)
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 22.dp)
                    ) {
                        Text(
                            text = "Descubre el universo de las paletas Drácula",
                            style = MaterialTheme.typography.headlineLarge,
                            color = BoneParchment,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        AntiqueDivider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Archivo histórico cronológico de sus colecciones, monstruos, criaturas y reliquias desde 1989 hasta 2026.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = WarmAlabaster.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = onEnterMuseum,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CrimsonVelvet,
                        contentColor = BoneParchment
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.5.dp, AntiqueGold),
                    contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("enter_museum_button")
                ) {
                    Text(
                        text = "ENTRAR AL MUSEO",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = BurnishedGold
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null,
                        tint = BurnishedGold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Catálogo local sin conexión • Información verificada",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmAlabaster.copy(alpha = 0.65f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
