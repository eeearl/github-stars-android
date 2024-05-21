package com.eeearl.githubstars.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchUserScreen() {
    var text by remember { mutableStateOf("") }
    Column {
        Row {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Box {
                        Text(text = "검색어를 입력해주세요.", modifier = Modifier.align(Alignment.Center))
                    }
                },
                modifier = Modifier.weight(1.0f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            IconButton(
                onClick = {

                },
                modifier = Modifier
            ) {
                Icon(Icons.Sharp.Search, "search")
            }
        }
        LazyColumn(modifier = Modifier.weight(1.0f)) {

        }
    }
}

@Preview
@Composable
private fun SearchUserScreenPreview() {
    MaterialTheme {
        Surface {
            SearchUserScreen()
        }
    }
}