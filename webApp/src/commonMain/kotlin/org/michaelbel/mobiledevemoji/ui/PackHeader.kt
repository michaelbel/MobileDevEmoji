package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PackHeader(
    packName: String,
    packUrl: String
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = packName,
            modifier = Modifier.align(Alignment.CenterStart),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )

        GetPackButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            packUrl = packUrl
        )
    }
}