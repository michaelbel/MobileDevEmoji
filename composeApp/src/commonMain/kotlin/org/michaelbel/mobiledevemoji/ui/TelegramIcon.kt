@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.data.TELEGRAM_URL
import org.michaelbel.mobiledevemoji.data.TelegramIconRes

@Composable
fun TelegramIcon() {
    val uriHandler = LocalUriHandler.current

    IconButton(
        onClick = { uriHandler.openUri(TELEGRAM_URL) },
        modifier = Modifier.onPointerEvent(PointerEventType.Press) { uriHandler.openUri(TELEGRAM_URL) }
    ) {
        Icon(
            painter = painterResource(TelegramIconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}