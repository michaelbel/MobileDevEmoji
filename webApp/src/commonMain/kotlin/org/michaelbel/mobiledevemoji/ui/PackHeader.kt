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
import androidx.compose.ui.unit.sp
import org.michaelbel.mobiledevemoji.ktx.isMobileBrowser

@Composable
fun PackHeader(
    packName: String,
    packUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(if (isMobileBrowser()) 112.dp else 56.dp)
    ) {
        Text(
            text = packName,
            modifier = Modifier.align(Alignment.CenterStart),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = if (isMobileBrowser()) 44.sp else 22.sp)
        )

        GetPackButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            packUrl = packUrl
        )
    }
}