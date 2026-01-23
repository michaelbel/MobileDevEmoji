@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.data.Emoji
import org.michaelbel.mobiledevemoji.ui.theme.CloseIconRes

@Composable
fun IconPreviewDialog(
    emoji: Emoji,
    query: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetValue ->
            if (sheetValue == SheetValue.Hidden) {
                onDismissRequest()
            }
            true
        }
    )
    val scope = rememberCoroutineScope()

    fun hide() {
        scope.launch { sheetState.hide() }
    }

    val annotatedName = run {
        val name = emoji.emojiResponse.name
        val q = query.trim()
        when {
            q.isEmpty() -> AnnotatedString(name)
            else -> {
                val nameLc = name.lowercase()
                val qLc = q.lowercase()
                val highlightStyle = SpanStyle(
                    background = MaterialTheme.colorScheme.secondaryContainer,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                buildAnnotatedString {
                    var start = 0
                    var idx = nameLc.indexOf(qLc, start)
                    while (idx >= 0) {
                        append(name.substring(start, idx))
                        withStyle(highlightStyle) { append(name.substring(idx, idx + q.length)) }
                        start = idx + q.length
                        idx = nameLc.indexOf(qLc, start)
                    }
                    append(name.substring(start))
                }
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(72.dp))
                        .clip(RoundedCornerShape(72.dp))
                        .size(300.dp)
                ) {
                    SvgIcon(
                        painter = emoji.painter,
                        modifier = Modifier.size(300.dp)
                    )
                }

                Text(
                    text = annotatedName,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            IconButton(
                onClick = ::hide,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.TopEnd)
                    .pointerHoverIcon(PointerIcon.Hand)
            ) {
                Icon(
                    painter = painterResource(CloseIconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
