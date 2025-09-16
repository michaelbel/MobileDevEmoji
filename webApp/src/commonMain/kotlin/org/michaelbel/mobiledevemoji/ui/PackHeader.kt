package org.michaelbel.mobiledevemoji.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.michaelbel.mobiledevemoji.ui.theme.ChevronDown
import org.michaelbel.mobiledevemoji.ui.theme.ChevronUp

@Composable
fun PackHeader(
    packName: String,
    packUrl: String,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .wrapContentSize()
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = packName,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 0.dp, bottom = 8.dp)
                    .wrapContentSize(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )

            Icon(
                painter = painterResource(if (expanded) ChevronUp else ChevronDown),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 4.dp, end = 8.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        GetPackButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            packUrl = packUrl
        )
    }
}