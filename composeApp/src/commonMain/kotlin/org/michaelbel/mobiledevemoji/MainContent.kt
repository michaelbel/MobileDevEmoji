@file:OptIn(InternalResourceApi::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import org.michaelbel.mobiledevemoji.data.APP_NAME
import org.michaelbel.mobiledevemoji.data.EmojiResponse
import org.michaelbel.mobiledevemoji.ui.EmojiIcon
import org.michaelbel.mobiledevemoji.ui.FigmaIcon
import org.michaelbel.mobiledevemoji.ui.IconPreviewBox
import org.michaelbel.mobiledevemoji.ui.TelegramIcon

@Composable
fun MainContent() {
    var emojiList by remember { mutableStateOf<List<EmojiResponse>>(emptyList()) }
    var emojiPreviewVisible by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val jsonString = readResourceBytes("icons.json").decodeToString()
        val json = Json { ignoreUnknownKeys = true }
        emojiList = json.decodeFromString(jsonString)
    }

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = APP_NAME
                        )
                    },
                    actions = {
                        TelegramIcon()
                        FigmaIcon()
                    },
                    backgroundColor = Color(0xFF141414),
                    contentColor = Color.White
                )
            },
            backgroundColor = Color(0xFF141414)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 8),
                    modifier = Modifier
                        .width(856.dp)
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(emojiList) { emoji ->
                        EmojiIcon(
                            emoji = emoji,
                            onClick = { emojiId ->
                                emojiPreviewVisible = when {
                                    emojiId == emojiPreviewVisible -> null
                                    else -> emojiId
                                }
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = emojiPreviewVisible != null
                ) {
                    IconPreviewBox(
                        emoji = emojiList.find { it.id == emojiPreviewVisible } ?: EmojiResponse.Empty,
                        modifier = Modifier.padding(start = 64.dp),
                        onClick = { emojiPreviewVisible = null }
                    )
                }
            }
        }
    }
}