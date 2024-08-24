@file:OptIn(InternalResourceApi::class, ExperimentalMaterial3Api::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
import org.michaelbel.mobiledevemoji.ui.theme.AppTheme
import org.michaelbel.mobiledevemoji.ui.topbar.FigmaIcon
import org.michaelbel.mobiledevemoji.ui.topbar.TelegramIcon

@Composable
fun MainContent() {
    var emojiList by remember { mutableStateOf<List<Emoji>>(emptyList()) }
    var emojiPreviewVisible by remember { mutableStateOf<String?>(null) }

    var emojiCount by remember { mutableIntStateOf(0) }
    var emojiLoadedCount by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()
    scope.launch {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = readResourceBytes("icons.json").decodeToString()
        val emojiResponseList: List<EmojiResponse> = json.decodeFromString(jsonString)
        val emojiMutableList: MutableList<Emoji> = mutableListOf()
        emojiCount = emojiResponseList.count()
        emojiResponseList.forEachIndexed { index, emojiResponse ->
            val emojiPainter = readResourceBytes("images/${emojiResponse.id}.svg").svgPainter
            val emoji = Emoji(emojiResponse, emojiPainter)
            emojiMutableList.add(index, emoji)
            emojiLoadedCount = emojiMutableList.count()
        }
        emojiList = emojiMutableList
    }

    AppTheme {
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
            when {
                emojiList.isNotEmpty() -> {
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
                else -> {
                    Box(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.width(400.dp).wrapContentHeight()
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth().height(4.dp)
                            )

                            Text(
                                text = "Loading Icons...",
                                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            if (emojiCount != 0) {
                                Text(
                                    text = "$emojiLoadedCount of $emojiCount",
                                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}