package com.eeearl.githubstars.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.eeearl.githubstars.ui.local.SearchUserLocalViewModel
import com.eeearl.githubstars.util.ComposableLifecycle

@Composable
fun SearchLocalUserScreen(
    viewModel: SearchUserLocalViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        StickySearchBar(
            onClickSearchButton = { inputText ->
                viewModel.onSearchTextChanged(inputText)
            }
        )
        LazyColumn(modifier = Modifier.weight(1.0f)) {
            items(uiState.searchList) { user ->
                SearchUserRowItem(
                    id = user.id,
                    name = user.name,
                    thumbnailUrl = user.thumbnailUrl,
                    favorite = user.favorite,
                    onCheckedChange = { id, checked ->
                        if (checked.not()) {
                            viewModel.onCheckFavorite(id)
                        }
                    }
                )
            }
        }
    }

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onSearchTextChanged(uiState.text)
            }
            else -> {

            }
        }
    }
}

@Preview
@Composable
private fun SearchUserScreenPreview() {
    MaterialTheme {
        Surface {
//            SearchUserScreen()
        }
    }
}