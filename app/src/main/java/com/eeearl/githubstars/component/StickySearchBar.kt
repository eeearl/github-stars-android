package com.eeearl.githubstars.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun StickySearchBar(
    onClickSearchButton: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val hideKeyboardAction = { searchText: String ->
        keyboardController?.hide()
        focusManager.clearFocus()
        onClickSearchButton.invoke(searchText)
    }

    Row(
        modifier = Modifier.padding(end = 4.dp)
    ) {
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
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    hideKeyboardAction.invoke(text)
                }
            ),
            singleLine = true,
        )
        IconButton(
            onClick = {
                onClickSearchButton.invoke(text)
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(Icons.Sharp.Search, "search")
        }
    }
}