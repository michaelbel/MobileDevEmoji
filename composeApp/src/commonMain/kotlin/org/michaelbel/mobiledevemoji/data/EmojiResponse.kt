package org.michaelbel.mobiledevemoji.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("pack") val pack: Int,
    @SerialName("position") val position: Int
) {
    companion object {
        val Empty: EmojiResponse = EmojiResponse("", "", -1, -1)
    }
}