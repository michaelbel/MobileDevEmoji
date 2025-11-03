@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.mobiledevemoji

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.michaelbel.mobiledevemoji.data.APP_NAME
import org.michaelbel.mobiledevemoji.data.ActionMode
import org.michaelbel.mobiledevemoji.data.Emoji
import org.michaelbel.mobiledevemoji.data.EmojiResponse
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_1
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_2
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_3
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_4
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_5
import org.michaelbel.mobiledevemoji.data.TELEGRAM_PACK_6
import org.michaelbel.mobiledevemoji.data.filterBy
import org.michaelbel.mobiledevemoji.data.pack
import org.michaelbel.mobiledevemoji.data.pack1
import org.michaelbel.mobiledevemoji.data.pack2
import org.michaelbel.mobiledevemoji.data.pack3
import org.michaelbel.mobiledevemoji.data.pack4
import org.michaelbel.mobiledevemoji.data.pack5
import org.michaelbel.mobiledevemoji.data.pack6
import org.michaelbel.mobiledevemoji.data.searchBy
import org.michaelbel.mobiledevemoji.issue.NetworkClient
import org.michaelbel.mobiledevemoji.ktx.decodeJsonToString
import org.michaelbel.mobiledevemoji.ktx.emojiPainter
import org.michaelbel.mobiledevemoji.ktx.isMobileBrowser
import org.michaelbel.mobiledevemoji.ui.EmojiIcon
import org.michaelbel.mobiledevemoji.ui.FilterChips
import org.michaelbel.mobiledevemoji.ui.IconPreviewDialog
import org.michaelbel.mobiledevemoji.ui.PackHeader
import org.michaelbel.mobiledevemoji.ui.SearchWidget
import org.michaelbel.mobiledevemoji.ui.topbar.FiltersIcon
import org.michaelbel.mobiledevemoji.ui.topbar.GithubIcon
import org.michaelbel.mobiledevemoji.ui.topbar.SearchIcon
import org.michaelbel.mobiledevemoji.ui.topbar.TelegramIcon

@Composable
fun MainContent() {
    val emojiSnapshotStateList: SnapshotStateList<Emoji> = mutableStateListOf()
    var emojiList by remember { mutableStateOf<List<Emoji>>(emptyList()) }
    var filtersList by remember { mutableStateOf<List<String>>(emptyList()) }
    var emojiPreviewVisible by remember { mutableStateOf<String?>(null) }

    var currentActionMode: ActionMode by remember { mutableStateOf(ActionMode.None) }
    var selectedFilter by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val pack1 = emojiList.pack1.filterBy(selectedFilter).searchBy(searchQuery)
    val pack2 = emojiList.pack2.filterBy(selectedFilter).searchBy(searchQuery)
    val pack3 = emojiList.pack3.filterBy(selectedFilter).searchBy(searchQuery)
    val pack4 = emojiList.pack4.filterBy(selectedFilter).searchBy(searchQuery)
    val pack5 = emojiList.pack5.filterBy(selectedFilter).searchBy(searchQuery)
    val pack6 = emojiList.pack6.filterBy(selectedFilter).searchBy(searchQuery)

    var isSearchEmpty by remember { mutableStateOf(false) }
    isSearchEmpty = pack1.isEmpty() && pack2.isEmpty() && pack3.isEmpty() && pack4.isEmpty() && pack5.isEmpty()

    var isPack1Expanded by remember { mutableStateOf(true) }
    var isPack2Expanded by remember { mutableStateOf(true) }
    var isPack3Expanded by remember { mutableStateOf(true) }
    var isPack4Expanded by remember { mutableStateOf(true) }
    var isPack5Expanded by remember { mutableStateOf(true) }
    var isPack6Expanded by remember { mutableStateOf(true) }

    val json = Json { ignoreUnknownKeys = true }
    val scope = rememberCoroutineScope()

    val submittedQueries = remember { mutableStateListOf<String>() }
    val alreadySubmitted = searchQuery.trim().isNotEmpty() && submittedQueries.any { it.equals(searchQuery.trim(), ignoreCase = true) }

    LaunchedEffect(Unit) {
        emojiSnapshotStateList.addAll(Emoji.EmptyList.toList())
        emojiList = emojiSnapshotStateList.toList()

        val emojiResponseList = json.decodeFromString<List<EmojiResponse>>("icons.json".decodeJsonToString())

        filtersList = emojiResponseList.flatMap { it.filters.orEmpty() }.distinct().sorted()

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
                            GithubIcon()
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
                            filters = filtersList,
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
                if (pack1.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 1",
                            packUrl = TELEGRAM_PACK_1,
                            expanded = isPack1Expanded,
                            onClick = { isPack1Expanded = !isPack1Expanded }
                        )
                    }

                    if (isPack1Expanded) {
                        items(
                            items = pack1
                        ) { emoji ->
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

                if (pack2.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                    ) {
                        PackHeader(
                            packName = "Pack 2",
                            packUrl = TELEGRAM_PACK_2,
                            expanded = isPack2Expanded,
                            onClick = { isPack2Expanded = !isPack2Expanded },
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    if (isPack2Expanded) {
                        items(
                            items = pack2
                        ) { emoji ->
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

                if (pack3.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 3",
                            packUrl = TELEGRAM_PACK_3,
                            expanded = isPack3Expanded,
                            onClick = { isPack3Expanded = !isPack3Expanded },
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    if (isPack3Expanded) {
                        items(
                            items = pack3
                        ) { emoji ->
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

                if (pack4.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 4",
                            packUrl = TELEGRAM_PACK_4,
                            expanded = isPack4Expanded,
                            onClick = { isPack4Expanded = !isPack4Expanded },
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    if (isPack4Expanded) {
                        items(
                            items = pack4
                        ) { emoji ->
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

                if (pack5.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 5",
                            packUrl = TELEGRAM_PACK_5,
                            expanded = isPack5Expanded,
                            onClick = { isPack5Expanded = !isPack5Expanded },
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    if (isPack5Expanded) {
                        items(
                            items = pack5
                        ) { emoji ->
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

                if (pack6.isNotEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        PackHeader(
                            packName = "Pack 6",
                            packUrl = TELEGRAM_PACK_6,
                            expanded = isPack6Expanded,
                            onClick = { isPack6Expanded = !isPack6Expanded },
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }

                    if (isPack6Expanded) {
                        items(
                            items = pack6
                        ) { emoji ->
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

            if (isSearchEmpty) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ничего не нашлось",
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                    )

                    when {
                        alreadySubmitted -> {
                            Text(
                                text = buildAnnotatedString {
                                    append("Запрос на добавление ")
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(searchQuery.trim()) }
                                    append(" отправлен.\nМаксимальная благодарочка")
                                },
                                modifier = Modifier.padding(top = 16.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                            )
                        }
                        else -> {
                            Button(
                                onClick = {
                                    scope.launch {
                                        try {
                                            NetworkClient.createIssue(searchQuery.trim())
                                        } catch (e: Throwable) {
                                            e.printStackTrace()
                                        } finally {
                                            submittedQueries.add(searchQuery.trim())
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .wrapContentSize()
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append("Предложить добавить ")
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(searchQuery.trim()) }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (emojiPreviewVisible != null) {
            IconPreviewDialog(
                emoji = emojiList.find { it.emojiResponse.id == emojiPreviewVisible } ?: Emoji.Empty,
                query = if (currentActionMode is ActionMode.Search) searchQuery.trim() else "",
                modifier = Modifier,
                onDismissRequest = { emojiPreviewVisible = null }
            )
        }
    }
}