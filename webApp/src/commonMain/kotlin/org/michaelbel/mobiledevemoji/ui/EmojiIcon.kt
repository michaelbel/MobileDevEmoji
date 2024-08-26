@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.michaelbel.mobiledevemoji.data.Emoji

@Composable
fun EmojiIcon(
    emoji: Emoji,
    onClick: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }
    val iconSize: Dp by animateDpAsState(
        targetValue = if (active) 104.dp else 100.dp,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )

    Box(
        modifier = Modifier
            .background(Color.Unspecified)
            .clip(RoundedCornerShape(24.dp))
            .size(108.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .size(iconSize)
                .background(if (emoji.isNotEmpty) Color.Unspecified else Color.DarkGray)
                .clickable(enabled = emoji.isNotEmpty) { onClick(emoji.emojiResponse.id) }
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
        ) {
            SvgIcon(
                painter = emoji.painter,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}