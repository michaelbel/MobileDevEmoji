@file:OptIn(InternalResourceApi::class, ExperimentalMaterial3Api::class)

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import org.michaelbel.mobiledevemoji.data.APP_NAME
import org.michaelbel.mobiledevemoji.data.Emoji
import org.michaelbel.mobiledevemoji.data.EmojiResponse
import org.michaelbel.mobiledevemoji.svg.svgPainter
import org.michaelbel.mobiledevemoji.ui.EmojiIcon
import org.michaelbel.mobiledevemoji.ui.IconPreviewBox
import org.michaelbel.mobiledevemoji.ui.topbar.FigmaIcon
import org.michaelbel.mobiledevemoji.ui.topbar.TelegramIcon

@Composable
fun MainContent() {
    val emojiSnapshotStateList: SnapshotStateList<Emoji> = mutableStateListOf()
    var emojiList by mutableStateOf<List<Emoji>>(emptyList())
    var emojiPreviewVisible by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    scope.launch {
        emojiSnapshotStateList.addAll(Emoji.EmptyList.toList())
        emojiList = emojiSnapshotStateList.toList()

        val json = Json { ignoreUnknownKeys = true }
        val jsonString = readResourceBytes("icons.json").decodeToString()
        val emojiResponseList = json.decodeFromString<List<EmojiResponse>>(jsonString)
        emojiResponseList.forEachIndexed { index, emojiResponse ->
            val emojiPainter = readResourceBytes("images/${emojiResponse.id}.svg").svgPainter
            val currentEmoji = emojiSnapshotStateList[index]
            emojiSnapshotStateList[index] = currentEmoji.copy(emojiResponse = emojiResponse, painter = emojiPainter)
            emojiList = emojiSnapshotStateList.toList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = APP_NAME
                    )
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        FigmaIcon()
                        TelegramIcon()
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 8),
                modifier = Modifier
                    .width(856.dp)
                    .fillMaxHeight(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
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
                    emoji = emojiList.find { it.emojiResponse.id == emojiPreviewVisible } ?: Emoji.Empty,
                    modifier = Modifier.padding(start = 64.dp),
                    onClick = { emojiPreviewVisible = null }
                )
            }
        }
    }
}