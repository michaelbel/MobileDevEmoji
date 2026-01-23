package org.michaelbel.mobiledevemoji.issue

import kotlinx.serialization.Serializable

@Serializable
data class IssueRequest(
    val title: String,
    val body: String,
    val labels: List<String>
) {
    companion object {
        fun create(query: String): IssueRequest {
            return IssueRequest(
                title = "Добавить новое эмодзи: $query",
                body = "Добавить эмодзи «$query».\nОтправлено из приложения.",
                labels = listOf("feature request", "emoji")
            )
        }
    }
}
