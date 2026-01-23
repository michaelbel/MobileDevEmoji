package org.michaelbel.mobiledevemoji.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("filters") val filters: List<String>? = null
) {
    val isNotEmpty: Boolean
        get() = this != Empty

    companion object {
        val Empty = EmojiResponse("", "", null)
    }
}
