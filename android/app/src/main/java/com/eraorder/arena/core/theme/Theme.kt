package com.eraorder.arena.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ArenaColorScheme = darkColorScheme(
    primary = Bronze,
    onPrimary = WalnutDeep,
    primaryContainer = BronzeDeep,
    onPrimaryContainer = Parchment,
    secondary = LinkBlue,
    onSecondary = WalnutDeep,
    secondaryContainer = WalnutChip,
    onSecondaryContainer = Parchment,
    tertiary = BronzeHighlight,
    onTertiary = WalnutDeep,
    background = Walnut,
    onBackground = Parchment,
    surface = WalnutSurface,
    onSurface = Parchment,
    surfaceVariant = WalnutRaised,
    onSurfaceVariant = ParchmentDim,
    surfaceContainerHighest = WalnutChip,
    outline = ParchmentMuted,
    outlineVariant = WalnutChip,
    error = Ember,
    onError = WalnutDeep,
    scrim = WalnutDeep
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ArenaColorScheme,
        typography = AppTypography,
        content = content
    )
}
