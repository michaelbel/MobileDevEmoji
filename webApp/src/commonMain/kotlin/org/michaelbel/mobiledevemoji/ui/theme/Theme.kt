@file:Suppress("UnusedReceiverParameter")

package org.michaelbel.mobiledevemoji.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val lightColorScheme = lightColorScheme().copy(
        background = Background,
        onBackground = OnBackground,
        surface = Surface,
        onSurface = OnSurface
    )
    val darkColorScheme = darkColorScheme().copy(
        background = DarkBackground,
        onBackground = DarkOnBackground,
        surface = DarkSurface,
        onSurface = DarkOnSurface
    )
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme else lightColorScheme,
        content = content
    )
}