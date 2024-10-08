package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.ui.theme.TelegramPlaneIconRes

@Composable
fun GetPackButton(
    modifier: Modifier,
    packUrl: String
) {
    val uriHandler = LocalUriHandler.current

    Button(
        onClick = { uriHandler.openUri(packUrl) },
        modifier = modifier.wrapContentSize(),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color(0xFFEADDFF),
            contentColor = Color(0xFF21005D)
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(TelegramPlaneIconRes),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "Get on Telegram",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}