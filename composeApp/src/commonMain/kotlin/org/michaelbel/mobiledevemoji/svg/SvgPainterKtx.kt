package org.michaelbel.mobiledevemoji.svg

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.skia.Data
import org.jetbrains.skia.svg.SVGDOM

@Composable
fun loadSvgPainterFromBytes(
    bytes: ByteArray
): Painter {
    return remember {
        val svg = try {
            SVGDOM(Data.makeFromBytes(bytes))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        SvgPainter(svg)
    }
}

val ByteArray.svgPainter: Painter
    get() {
        val svg = try {
            SVGDOM(Data.makeFromBytes(this))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return SvgPainter(svg)
    }