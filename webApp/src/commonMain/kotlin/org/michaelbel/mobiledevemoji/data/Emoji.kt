package org.michaelbel.mobiledevemoji.data

import androidx.compose.ui.graphics.painter.Painter

data class Emoji(
    val emojiResponse: EmojiResponse,
    val painter: Painter?
) {
    val isNotEmpty: Boolean
        get() = emojiResponse.isNotEmpty && painter != null

    companion object {
        val Empty: Emoji = Emoji(EmojiResponse.Empty, null)
        val EmptyList: MutableList<Emoji> = MutableList(size = 200) { index ->
            Emoji(EmojiResponse.Empty.copy(position = index), null)
        }
    }
}