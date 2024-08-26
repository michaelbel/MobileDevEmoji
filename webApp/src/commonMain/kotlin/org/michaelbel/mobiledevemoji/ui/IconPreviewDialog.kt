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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.data.CloseIconRes
import org.michaelbel.mobiledevemoji.data.Emoji

@Composable
fun IconPreviewDialog(
    emoji: Emoji,
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
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(72.dp)
                        )
                        .clip(RoundedCornerShape(72.dp))
                        .size(300.dp)
                ) {
                    SvgIcon(
                        painter = emoji.painter,
                        modifier = Modifier.size(300.dp)
                    )
                }

                Text(
                    text = emoji.emojiResponse.name,
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
            ) {
                Icon(
                    painter = painterResource(CloseIconRes),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}