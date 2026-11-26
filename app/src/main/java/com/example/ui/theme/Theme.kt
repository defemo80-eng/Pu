package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val DracuversusDarkColorScheme = darkColorScheme(
    primary = AntiqueGold,
    onPrimary = ObsidianBlack,
    primaryContainer = DarkBronzeGold,
    onPrimaryContainer = BoneParchment,
    secondary = CrimsonVelvet,
    onSecondary = BoneParchment,
    secondaryContainer = DeepGarnet,
    onSecondaryContainer = WarmAlabaster,
    tertiary = MistViolet,
    onTertiary = BoneParchment,
    background = ObsidianBlack,
    onBackground = BoneParchment,
    surface = CryptShadow,
    onSurface = BoneParchment,
    surfaceVariant = NocturnalPlum,
    onSurfaceVariant = MutedPewter,
    surfaceContainerLowest = ObsidianBlack,
    surfaceContainerLow = CryptShadow,
    surfaceContainer = NocturnalPlum,
    surfaceContainerHigh = VelvetAmethyst,
    surfaceContainerHighest = MistViolet,
    outline = StoneBorder,
    outlineVariant = DarkBronzeGold
)

val DracuversusShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun DracuversusTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DracuversusDarkColorScheme,
        typography = DracuversusTypography,
        shapes = DracuversusShapes,
        content = content
    )
}
