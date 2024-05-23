package com.eeearl.githubstars.ui.local

import androidx.lifecycle.ViewModel
import com.eeearl.githubstars.db.DatabaseService
import com.eeearl.githubstars.ui.SearchUserRowLocalItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface SearchUserLocalCallback<T> {
    fun onFetch(data: List<T>)
}

class SearchUserLocalViewModel(
    private val databaseService: DatabaseService,
): ViewModel(), SearchUserLocalCallback<SearchUserRowLocalItem> {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchTextChanged(text: CharSequence) {
        _uiState.update {
            it.copy(text = text.toString())
        }
        databaseService.selectGithubUsers(text.toString(), this)
    }

    override fun onFetch(data: List<SearchUserRowLocalItem>) {
        _uiState.value = _uiState.value.copy(searchList = data)
    }

    fun onCheckFavorite(id: Int) {
        databaseService.deleteUser(id)
        onSearchTextChanged(_uiState.value.text)
    }

    data class UiState(
        val searchList: List<SearchUserRowLocalItem> = emptyList(),
        val text: String = "",
    )
}

