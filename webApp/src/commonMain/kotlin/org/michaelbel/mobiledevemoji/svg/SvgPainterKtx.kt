package org.michaelbel.mobiledevemoji.svg

import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.skia.Data
import org.jetbrains.skia.svg.SVGDOM

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