@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.github.panpf.sketch.PlatformContext
import com.github.panpf.sketch.SingletonSketch
import com.github.panpf.sketch.Sketch
import com.github.panpf.sketch.decode.supportSvg
import org.jetbrains.compose.resources.configureWebResources
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    initialSketch()
    configureWebResources {
        resourcePathMapping { path -> "./$path" }
    }
    onWasmReady {
        CanvasBasedWindow(
            title = "MobileDevEmoji",
            canvasElementId = "ComposeTarget"
        ) {
            MainContent()
        }
    }
}

private fun initialSketch() {
    val context = PlatformContext.INSTANCE
    SingletonSketch.setSafe {
        Sketch.Builder(context).apply {
            components {
                supportSvg()
            }
        }.build()
    }
}