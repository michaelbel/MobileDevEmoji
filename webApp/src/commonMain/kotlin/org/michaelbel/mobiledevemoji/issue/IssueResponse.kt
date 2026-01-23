package org.michaelbel.mobiledevemoji.issue

import kotlinx.serialization.Serializable

@Serializable
data class IssueResponse(
    val html_url: String,
    val number: Int
)
