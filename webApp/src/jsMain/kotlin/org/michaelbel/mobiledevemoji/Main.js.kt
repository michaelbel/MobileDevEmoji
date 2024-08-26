@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.compose.resources.configureWebResources
import org.jetbrains.skiko.wasm.onWasmReady
import org.michaelbel.mobiledevemoji.ui.theme.AppTheme

fun main() {
    configureWebResources {
        resourcePathMapping { path -> "./$path" }
    }
    onWasmReady {
        CanvasBasedWindow(
            title = "MobileDevEmoji",
            canvasElementId = "ComposeTarget"
        ) {
            AppTheme {
                MainContent()
            }
        }
    }
}