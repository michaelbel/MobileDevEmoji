@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
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
import org.michaelbel.mobiledevemoji.ktx.isMobileBrowser

@Composable
fun EmojiIcon(
    emoji: Emoji,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }
    val iconSize: Dp by animateDpAsState(
        targetValue = if (active || selected) 104.dp else 100.dp,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )

    val boxModifier = if (isMobileBrowser()) {
        Modifier.clip(RoundedCornerShape(0.dp)).aspectRatio(1F).fillMaxSize()
    } else {
        Modifier.clip(RoundedCornerShape(24.dp)).size(108.dp)
    }

    val innerBoxModifier = if (isMobileBrowser()) {
        Modifier.clip(RoundedCornerShape(0.dp)).fillMaxSize().background(Color.Unspecified)
    } else {
        Modifier.clip(RoundedCornerShape(24.dp)).size(iconSize).background(if (emoji.isNotEmpty) Color.Unspecified else Color.DarkGray)
    }

    Box(
        modifier = Modifier
            .background(Color.Unspecified)
            .then(boxModifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = innerBoxModifier
                .clickable(
                    enabled = emoji.isNotEmpty,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick(emoji.emojiResponse.id) }
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false },
            contentAlignment = Alignment.Center
        ) {
            SvgIcon(
                painter = emoji.painter,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}