package com.eeearl.githubstars.ui.local

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.eeearl.githubstars.ui.SearchUserRowLocalItem

interface SearchUserLocalCallback<T> {
    fun onFetch(data: List<T>)
}

class SearchUserLocalViewModel: ViewModel(),
    SearchUserLocalCallback<SearchUserRowLocalItem> {

    val mList = ObservableArrayList<SearchUserRowLocalItem>()
    val mSearchText = ObservableField<String>()

    fun onSearchTextChanged(text: CharSequence) {
        mSearchText.set(text.toString())
    }

    override fun onFetch(data: List<SearchUserRowLocalItem>) {
        mList.clear()
        mList.addAll(data)
    }
}

