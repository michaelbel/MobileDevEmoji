package org.michaelbel.mobiledevemoji.ui.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.ui.theme.FilterOffRes
import org.michaelbel.mobiledevemoji.ui.theme.FilterOnRes

@Composable
fun FiltersIcon(
    isFiltersEnabled: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
    ) {
        Icon(
            painter = painterResource(if (isFiltersEnabled) FilterOffRes else FilterOnRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
