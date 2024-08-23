@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mobiledevemoji.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.michaelbel.mobiledevemoji.data.EmojiResponse

@Composable
fun EmojiIcon(
    emoji: EmojiResponse,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .size(100.dp)
            .clickable { onClick(emoji.id) }
    ) {
        SvgIcon(
            path = Res.getUri("files/${emoji.id}.svg"),
            modifier = Modifier.size(100.dp)
        )
    }
}