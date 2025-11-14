@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji.ui.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.data.TELEGRAM_URL
import org.michaelbel.mobiledevemoji.ui.theme.TelegramIconRes

@Composable
fun TelegramIcon(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    IconButton(
        onClick = { uriHandler.openUri(TELEGRAM_URL) },
        modifier = modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .onPointerEvent(PointerEventType.Press) { uriHandler.openUri(TELEGRAM_URL) }
    ) {
        Icon(
            painter = painterResource(TelegramIconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}