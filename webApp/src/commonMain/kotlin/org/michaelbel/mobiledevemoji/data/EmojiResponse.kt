package org.michaelbel.mobiledevemoji.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
) {
    val isNotEmpty: Boolean
        get() = this != Empty

    companion object {
        val Empty: EmojiResponse = EmojiResponse("", "")
    }
}