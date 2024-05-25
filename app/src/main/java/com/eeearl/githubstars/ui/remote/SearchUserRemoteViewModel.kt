package com.eeearl.githubstars.ui.remote

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eeearl.githubstars.db.DatabaseService
import com.eeearl.githubstars.net.ApiClient
import com.eeearl.githubstars.net.model.SearchUserRequestData
import com.eeearl.githubstars.net.model.SearchUserResponse
import com.eeearl.githubstars.ui.SearchUserListAdapter
import com.eeearl.githubstars.ui.SearchUserRowItemType
import com.eeearl.githubstars.ui.SearchUserRowRemoteItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchUserBindingAdapter {
    @JvmStatic
    @BindingAdapter("searchUserListItems")
    fun RecyclerView.setList(list: List<SearchUserRowItemType>) {
        val adapter = this.adapter as SearchUserListAdapter
        adapter.setList(list)
    }

    @JvmStatic
    @BindingAdapter("imageFromUrl")
    fun AppCompatImageView.setImageUrl(url: String) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

class SearchUserRemoteViewModel(
    private val databaseService: DatabaseService,
): ViewModel() {


    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchTextChanged(text: CharSequence) {
        _uiState.update {
            it.copy(text = text.toString())
        }

        val req = SearchUserRequestData(text.toString(), "desc", 1, 100)
        ApiClient.searchUserInGithub(req, object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                when (response.isSuccessful) {
                    true -> {
                        val list = ArrayList<SearchUserRowRemoteItem>()

                        println("list")
                        response.body()?.userItems?.
                        filter { it.name != null }?.
                        forEach {
                            val fav = databaseService.existUser(it.id)
                            val rowItem = SearchUserRowRemoteItem(it.id, it.name ?: "", it.avatarUrl, fav)

                            if (it.name != null) {
                                list.add(rowItem)
                            }
                        }

                        _uiState.update {
                            it.copy(searchList = list)
                        }
                    }
                    else -> return
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                println(t.toString())
            }
        })
    }

    fun onCheckFavorite(user: SearchUserRowRemoteItem, isChecked: Boolean) {
        if (databaseService.existUser(user.id)) {
            databaseService.deleteUser(user.id)
        } else {
            databaseService.insertUser(user.id, user.name, user.thumbnailUrl)
        }

        val newItemIndex = _uiState.value.searchList.indexOf(user)
        val newList = _uiState.value.searchList.mapIndexed { index, searchUserRowRemoteItem ->
            if (index == newItemIndex) {
                searchUserRowRemoteItem.copy(favorite = isChecked)
            } else {
                searchUserRowRemoteItem
            }
        }

        _uiState.update {
            it.copy(
                searchList = newList
            )
        }
    }

    data class UiState(
        val searchList: List<SearchUserRowRemoteItem> = emptyList(),
        val text: String = "",
    )
}

