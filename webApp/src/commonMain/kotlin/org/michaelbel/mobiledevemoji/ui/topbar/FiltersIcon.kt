@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.mobiledevemoji.ui.topbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
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
        modifier = Modifier.onPointerEvent(PointerEventType.Press) { onClick() }
    ) {
        Icon(
            painter = painterResource(if (isFiltersEnabled) FilterOffRes else FilterOnRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}