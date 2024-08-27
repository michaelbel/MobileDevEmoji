@file:OptIn(InternalResourceApi::class)

package org.michaelbel.mobiledevemoji.ktx

import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import org.michaelbel.mobiledevemoji.svg.svgPainter

suspend fun String.decodeJsonToString(): String {
    return readResourceBytes(this).decodeToString()
}

suspend fun String.emojiPainter(): Painter? {
    return try {
        readResourceBytes(this).svgPainter
    } catch (e: Exception) {
        null
    }
}