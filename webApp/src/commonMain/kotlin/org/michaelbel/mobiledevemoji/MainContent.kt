@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.michaelbel.mobiledevemoji.data.APP_NAME
import org.michaelbel.mobiledevemoji.data.ActionMode
import org.michaelbel.mobiledevemoji.data.Emoji
import org.michaelbel.mobiledevemoji.data.EmojiResponse
import org.michaelbel.mobiledevemoji.data.FILTERS
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_1
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_2
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_3
import org.michaelbel.mobiledevemoji.data.filterBy
import org.michaelbel.mobiledevemoji.data.pack
import org.michaelbel.mobiledevemoji.data.pack1
import org.michaelbel.mobiledevemoji.data.pack2
import org.michaelbel.mobiledevemoji.data.pack3
import org.michaelbel.mobiledevemoji.data.searchBy
import org.michaelbel.mobiledevemoji.ktx.decodeJsonToString
import org.michaelbel.mobiledevemoji.ktx.emojiPainter
import org.michaelbel.mobiledevemoji.ktx.isMobileBrowser
import org.michaelbel.mobiledevemoji.ui.EmojiIcon
import org.michaelbel.mobiledevemoji.ui.FilterChips
import org.michaelbel.mobiledevemoji.ui.IconPreviewDialog
import org.michaelbel.mobiledevemoji.ui.PackHeader
import org.michaelbel.mobiledevemoji.ui.SearchWidget
import org.michaelbel.mobiledevemoji.ui.topbar.FigmaIcon
import org.michaelbel.mobiledevemoji.ui.topbar.FiltersIcon
import org.michaelbel.mobiledevemoji.ui.topbar.SearchIcon
import org.michaelbel.mobiledevemoji.ui.topbar.TelegramIcon

@Composable
fun MainContent() {
    val emojiSnapshotStateList: SnapshotStateList<Emoji> = mutableStateListOf()
    var emojiList by remember { mutableStateOf<List<Emoji>>(emptyList()) }
    var emojiPreviewVisible by remember { mutableStateOf<String?>(null) }

    var currentActionMode: ActionMode by remember { mutableStateOf(ActionMode.None) }
    var selectedFilter by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val json = Json { ignoreUnknownKeys = true }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        emojiSnapshotStateList.addAll(Emoji.EmptyList.toList())
        emojiList = emojiSnapshotStateList.toList()

        val emojiResponseList = json.decodeFromString<List<EmojiResponse>>("icons.json".decodeJsonToString())
        emojiResponseList.forEachIndexed { index, emojiResponse ->
            scope.launch(Dispatchers.Default) {
                val emojiPainter = "${index.pack}/${emojiResponse.id}.svg".emojiPainter()
                val currentEmoji = emojiSnapshotStateList[index]
                withContext(Dispatchers.Main) {
                    emojiSnapshotStateList[index] = currentEmoji.copy(emojiResponse = emojiResponse, painter = emojiPainter)
                    emojiList = emojiSnapshotStateList.toList()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = APP_NAME
                            )
                        }
                    },
                    actions = {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SearchIcon(
                                onClick = { currentActionMode = if (currentActionMode is ActionMode.Search) ActionMode.None else ActionMode.Search }
                            )
                            FiltersIcon(
                                isFiltersEnabled = currentActionMode is ActionMode.Filters,
                                onClick = {
                                    currentActionMode = if (currentActionMode is ActionMode.Filters) ActionMode.None else ActionMode.Filters
                                    if (currentActionMode !is ActionMode.Filters) {
                                        selectedFilter = ""
                                    }
                                }
                            )
                            FigmaIcon()
                            TelegramIcon()
                        }
                    }
                )
                when (currentActionMode) {
                    is ActionMode.Search -> {
                        SearchWidget(
                            query = searchQuery,
                            onQueryChanged = { query ->
                                searchQuery = query
                            }
                        )
                    }
                    else -> searchQuery = ""
                }
                when (currentActionMode) {
                    is ActionMode.Filters -> {
                        FilterChips(
                            filters = FILTERS,
                            selectedFilter = selectedFilter,
                            onFilterSelected = { filter ->
                                selectedFilter = if (selectedFilter == filter) "" else filter
                            }
                        )
                    }
                    else -> selectedFilter = ""
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val horizontalArrangementDp = if (isMobileBrowser()) 8.dp else 0.dp
            val verticalArrangement = if (isMobileBrowser()) 8.dp else 0.dp
            val gridModifier = if (isMobileBrowser()) Modifier.fillMaxSize() else Modifier.width(888.dp).fillMaxHeight() // 100*8 + 16*2 + 8*7

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 8),
                modifier = gridModifier,
                horizontalArrangement = Arrangement.spacedBy(horizontalArrangementDp),
                verticalArrangement = Arrangement.spacedBy(verticalArrangement),
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                val pack1 = emojiList.pack1.filterBy(selectedFilter).searchBy(searchQuery)
                if (pack1.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 1",
                            packUrl = TELEGRAM_PACK_1
                        )
                    }

                    items(pack1) { emoji ->
                        EmojiIcon(
                            emoji = emoji,
                            selected = emoji.emojiResponse.id == emojiPreviewVisible,
                            onClick = { emojiId ->
                                emojiPreviewVisible = when {
                                    emojiId == emojiPreviewVisible -> null
                                    else -> emojiId
                                }
                            }
                        )
                    }
                }

                val pack2 = emojiList.pack2.filterBy(selectedFilter).searchBy(searchQuery)
                if (pack2.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 2",
                            packUrl = TELEGRAM_PACK_2,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    items(pack2) { emoji ->
                        EmojiIcon(
                            emoji = emoji,
                            selected = emoji.emojiResponse.id == emojiPreviewVisible,
                            onClick = { emojiId ->
                                emojiPreviewVisible = when {
                                    emojiId == emojiPreviewVisible -> null
                                    else -> emojiId
                                }
                            }
                        )
                    }
                }

                val pack3 = emojiList.pack3.filterBy(selectedFilter).searchBy(searchQuery)
                if (pack3.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 3",
                            packUrl = TELEGRAM_PACK_3,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    items(pack3) { emoji ->
                        EmojiIcon(
                            emoji = emoji,
                            selected = emoji.emojiResponse.id == emojiPreviewVisible,
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