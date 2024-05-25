package com.eeearl.githubstars.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchUserRowItem(
    id: Int,
    name: String,
    thumbnailUrl: String,
    favorite: Boolean,
    onCheckedChange: (Int, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        GlideImage(
            model = thumbnailUrl,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(40.dp))
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = name,
            modifier = Modifier
                .weight(1.0f)
                .align(Alignment.CenterVertically)
        )
        Checkbox(
            checked = favorite,
            onCheckedChange = {
                onCheckedChange.invoke(id, it)
            }
        )
    }
}

@Preview
@Composable
private fun SearchUserRowItemPreview() {
    MaterialTheme {
        Surface {
//            SearchUserRowItem()
        }
    }
}