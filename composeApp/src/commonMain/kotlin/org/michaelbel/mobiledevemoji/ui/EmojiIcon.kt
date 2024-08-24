package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.michaelbel.mobiledevemoji.data.Emoji

@Composable
fun EmojiIcon(
    emoji: Emoji,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .size(100.dp)
            .clickable { onClick(emoji.emojiResponse.id) }
    ) {
        SvgIcon(
            painter = emoji.painter,
            modifier = Modifier.size(100.dp)
        )
    }
}