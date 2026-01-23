package org.michaelbel.mobiledevemoji.issue

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.michaelbel.mobiledevemoji.BuildKonfig

object NetworkClient {

    private val httpClient by lazy {
        HttpClient {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            defaultRequest {
                header("Accept", "application/vnd.github+json")
                header("Authorization", "Bearer " + BuildKonfig.GH_TOKEN)
            }
        }
    }

    suspend fun createIssue(query: String): IssueResponse {
        val resp = httpClient.post("https://api.github.com/repos/michaelbel/MobileDevEmoji/issues") {
            contentType(ContentType.Application.Json)
            setBody(IssueRequest.create(query))
        }
        if (resp.status.value !in 200..299) {
            throw IllegalStateException("GitHub error: ${resp.status} ${resp.bodyAsText()}")
        }
        return resp.body()
    }
}
