@file:OptIn(InternalResourceApi::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.rememberAsyncImageState
import com.github.panpf.sketch.request.ComposableImageRequest
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import org.jetbrains.skia.Data
import org.jetbrains.skia.Rect
import org.jetbrains.skia.svg.SVGDOM
import org.jetbrains.skia.svg.SVGLength
import org.jetbrains.skia.svg.SVGLengthUnit

// sample path: https://raw.githubusercontent.com/JetBrains/compose-multiplatform/master/artwork/idea-logo.svg
@Composable
fun SvgIcon(
    path: String,
    modifier: Modifier
) {
    val imageState = rememberAsyncImageState()
    val request = ComposableImageRequest(path)

    var resBytes by remember { mutableStateOf<ByteArray?>(null) }
    LaunchedEffect(Unit) {
        resBytes = readResourceBytes("images/coil.svg")
    }

    if (resBytes != null) {
        Image(
            painter = loadSvgPainterFromBytes(resBytes!!),
            modifier = modifier,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

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

class SvgPainter(
    private val svg: SVGDOM?,
): Painter() {
    override var intrinsicSize: Size = Size.Unspecified

    override fun DrawScope.onDraw() {
        if (svg == null) return

        val svgWidth: Float
        val svgHeight: Float
        val viewBox: Rect? = svg.root?.viewBox

        if (viewBox != null) {
            svgWidth = viewBox.width
            svgHeight = viewBox.height
        } else {
            svgWidth = svg.root?.width?.value ?: 0f
            svgHeight = svg.root?.height?.value ?: 0f
        }

        // Set the SVG's view box to enable scaling if it is not set.
        if (viewBox == null && svgWidth > 0f && svgHeight > 0f) {
            svg.root?.viewBox = Rect.makeWH(svgWidth, svgHeight)
        }

        svg.root?.width = SVGLength(
            value = 100f,
            unit = SVGLengthUnit.PERCENTAGE,
        )
        svg.root?.height = SVGLength(
            value = 100f,
            unit = SVGLengthUnit.PERCENTAGE,
        )
        svg.setContainerSize(size.width, size.height)
        svg.render(drawContext.canvas.nativeCanvas)
    }
}