package org.michaelbel.mobiledevemoji.data

import androidx.compose.ui.graphics.painter.Painter

data class Emoji(
    val emojiResponse: EmojiResponse,
    val painter: Painter?
) {
    companion object {
        val Empty: Emoji = Emoji(EmojiResponse.Empty, null)
    }
}