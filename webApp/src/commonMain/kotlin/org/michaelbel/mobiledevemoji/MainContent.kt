@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.michaelbel.mobiledevemoji.data.APP_NAME
import org.michaelbel.mobiledevemoji.data.Emoji
import org.michaelbel.mobiledevemoji.data.EmojiResponse
import org.michaelbel.mobiledevemoji.data.PACK_1_SIZE
import org.michaelbel.mobiledevemoji.data.PACK_2_SIZE
import org.michaelbel.mobiledevemoji.data.PACK_3_SIZE
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_1
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_2
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_3
import org.michaelbel.mobiledevemoji.ktx.decodeJsonToString
import org.michaelbel.mobiledevemoji.ktx.emojiPainter
import org.michaelbel.mobiledevemoji.ui.EmojiIcon
import org.michaelbel.mobiledevemoji.ui.IconPreviewDialog
import org.michaelbel.mobiledevemoji.ui.PackHeader
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

        val emojiResponseList1 = json.decodeFromString<List<EmojiResponse>>("icons-pack1.json".decodeJsonToString())
        emojiResponseList1.forEachIndexed { index, emojiResponse ->
            val emojiPainter = "pack1/${emojiResponse.id}.svg".emojiPainter()
            val currentEmoji = emojiSnapshotStateList[index]
            emojiSnapshotStateList[index] = currentEmoji.copy(emojiResponse = emojiResponse, painter = emojiPainter)
            emojiList = emojiSnapshotStateList.toList()
        }

        val emojiResponseList2 = json.decodeFromString<List<EmojiResponse>>("icons-pack2.json".decodeJsonToString())
        val position = PACK_1_SIZE
        emojiResponseList2.forEachIndexed { index, emojiResponse ->
            val emojiPainter = "pack2/${emojiResponse.id}.svg".emojiPainter()
            val currentEmoji = emojiSnapshotStateList[index.plus(position)]
            emojiSnapshotStateList[index.plus(position)] = currentEmoji.copy(emojiResponse = emojiResponse, painter = emojiPainter)
            emojiList = emojiSnapshotStateList.toList()
        }

        val emojiResponseList3 = json.decodeFromString<List<EmojiResponse>>("icons-pack3.json".decodeJsonToString())
        val position2 = PACK_1_SIZE + PACK_2_SIZE
        emojiResponseList3.forEachIndexed { index, emojiResponse ->
            val emojiPainter = "pack3/${emojiResponse.id}.svg".emojiPainter()
            val currentEmoji = emojiSnapshotStateList[index.plus(position2)]
            emojiSnapshotStateList[index.plus(position2)] = currentEmoji.copy(emojiResponse = emojiResponse, painter = emojiPainter)
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 8),
                modifier = Modifier
                    .width(888.dp) // 100*8 + 16*2 + 8*7
                    .fillMaxHeight(),
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    PackHeader(
                        packName = "Pack 1",
                        packUrl = TELEGRAM_PACK_1
                    )
                }

                items(emojiList.take(PACK_1_SIZE)) { emoji ->
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

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    PackHeader(
                        packName = "Pack 2",
                        packUrl = TELEGRAM_PACK_2,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }

                items(emojiList.subList(PACK_1_SIZE, PACK_1_SIZE.plus(PACK_2_SIZE))) { emoji ->
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

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    PackHeader(
                        packName = "Pack 3",
                        packUrl = TELEGRAM_PACK_3,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }

                items(emojiList.takeLast(PACK_3_SIZE)) { emoji ->
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
        }

        if (emojiPreviewVisible != null) {
            IconPreviewDialog(
                emoji = emojiList.find { it.emojiResponse.id == emojiPreviewVisible } ?: Emoji.Empty,
                modifier = Modifier,
                onDismissRequest = { emojiPreviewVisible = null }
            )
        }
    }
}