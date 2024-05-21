package com.eeearl.githubstars.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchUserRowItem() {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "User Name",
            modifier = Modifier
                .weight(1.0f)
                .align(Alignment.CenterVertically)
        )
        Checkbox(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Preview
@Composable
private fun SearchUserRowItemPreview() {
    MaterialTheme {
        Surface {
            SearchUserRowItem()
        }
    }
}