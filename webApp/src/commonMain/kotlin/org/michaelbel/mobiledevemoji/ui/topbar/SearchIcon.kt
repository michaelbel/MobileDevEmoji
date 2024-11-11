package org.michaelbel.mobiledevemoji.ui.topbar

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.ktx.isMobileBrowser
import org.michaelbel.mobiledevemoji.ui.theme.SearchRes

@Composable
fun SearchIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(if (isMobileBrowser() && false) 84.dp else 48.dp)
    ) {
        Icon(
            painter = painterResource(SearchRes),
            contentDescription = null,
            modifier = Modifier.size(if (isMobileBrowser() && false) 48.dp else 24.dp),
            tint = Color.Unspecified
        )
    }
}