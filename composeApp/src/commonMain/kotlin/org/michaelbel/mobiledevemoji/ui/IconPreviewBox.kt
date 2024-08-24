package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.michaelbel.mobiledevemoji.data.Emoji

@Composable
fun IconPreviewBox(
    emoji: Emoji,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.width(332.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(72.dp))
                .size(300.dp)
                .clickable { onClick() }
        ) {
            SvgIcon(
                painter = emoji.painter,
                modifier = Modifier.size(300.dp)
            )
        }

        Text(
            text = emoji.emojiResponse.name,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}