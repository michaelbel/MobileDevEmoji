package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
fun SvgIcon(
    painter: Painter?,
    modifier: Modifier
) {
    if (painter != null) {
        Image(
            painter = painter,
            modifier = modifier,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}