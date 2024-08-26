@file:Suppress("UnusedReceiverParameter")

package org.michaelbel.mobiledevemoji.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val scheme = darkColorScheme().copy(
        background = Background,
        onBackground = OnBackground,
        surface = Surface,
        onSurface = OnSurface
    )

    MaterialTheme(
        colorScheme = scheme,
        content = content
    )
}