package org.michaelbel.mobiledevemoji.ui.topbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.ui.theme.SearchRes

@Composable
fun SearchIcon(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(SearchRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}