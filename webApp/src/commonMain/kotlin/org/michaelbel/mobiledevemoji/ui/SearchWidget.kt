@file:OptIn(ExperimentalLayoutApi::class)

package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.ktx.isMobileBrowser
import org.michaelbel.mobiledevemoji.ui.theme.ClearRes

@Composable
fun SearchWidget(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        FlowRow(
            modifier = Modifier.then(if (isMobileBrowser()) Modifier.padding(horizontal = 16.dp).fillMaxWidth() else Modifier.width(888.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = query,
                onValueChange = onQueryChanged,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onQueryChanged("")
                                focusRequester.requestFocus()
                            }
                        ) {
                            Icon(
                                painter = painterResource(ClearRes),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.colors().copy(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Search..."
                    )
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}