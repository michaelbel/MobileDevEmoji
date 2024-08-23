package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.rememberAsyncImagePainter
import com.github.panpf.sketch.rememberAsyncImageState
import com.github.panpf.sketch.request.ComposableImageRequest

@Composable
fun SvgIcon(
    path: String,
    modifier: Modifier
) {
    val imageState = rememberAsyncImageState()
    val request = ComposableImageRequest(path)

    Image(
        painter = rememberAsyncImagePainter(
            request = request,
            state = imageState,
            contentScale = ContentScale.Crop
        ),
        modifier = modifier,
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}