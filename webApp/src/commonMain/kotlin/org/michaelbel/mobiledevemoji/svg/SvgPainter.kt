package org.michaelbel.mobiledevemoji.svg

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.skia.Rect
import org.jetbrains.skia.svg.SVGDOM
import org.jetbrains.skia.svg.SVGLength
import org.jetbrains.skia.svg.SVGLengthUnit

class SvgPainter(
    private val svg: SVGDOM?
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
            svgWidth = svg.root?.width?.value ?: 0F
            svgHeight = svg.root?.height?.value ?: 0F
        }

        if (viewBox == null && svgWidth > 0F && svgHeight > 0F) {
            svg.root?.viewBox = Rect.makeWH(svgWidth, svgHeight)
        }

        svg.root?.width = SVGLength(100F, SVGLengthUnit.PERCENTAGE,)
        svg.root?.height = SVGLength(100F, SVGLengthUnit.PERCENTAGE,)
        svg.setContainerSize(size.width, size.height)
        svg.render(drawContext.canvas.nativeCanvas)
    }
}
