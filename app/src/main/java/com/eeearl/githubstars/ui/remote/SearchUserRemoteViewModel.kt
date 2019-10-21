package com.eeearl.githubstars.ui.remote

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eeearl.githubstars.db.DatabaseService
import com.eeearl.githubstars.net.model.SearchUserResponse
import com.eeearl.githubstars.ui.SearchUserListAdapter
import com.eeearl.githubstars.ui.SearchUserRowItemType
import com.eeearl.githubstars.ui.SearchUserRowRemoteItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchUserBindingAdapter {
    @JvmStatic @BindingAdapter("searchUserListItems")
    fun RecyclerView.setList(list: List<SearchUserRowItemType>) {
        val adapter = this.adapter as SearchUserListAdapter
        adapter.setList(list)
    }

    @JvmStatic @BindingAdapter("imageFromUrl")
    fun AppCompatImageView.setImageUrl(url: String) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

class SearchUserRemoteViewModel: ViewModel(), Callback<SearchUserResponse> {

    val mList = ObservableArrayList<SearchUserRowRemoteItem>()
    val mSearchText = ObservableField<String>()
    lateinit var mContext: Context

    fun onSearchTextChanged(text: CharSequence) {
        mSearchText.set(text.toString())
    }

    override fun onFailure(call: Call<SearchUserResponse>?, t: Throwable?) {
        println(t.toString())
    }

    override fun onResponse(call: Call<SearchUserResponse>?, response: Response<SearchUserResponse>) {

        when (response.isSuccessful) {
            true -> {
                var list = ArrayList<SearchUserRowRemoteItem>()

                response.body()?.userItems?.
                    filter { it.name != null }?.
                    forEach {
                    val fav = DatabaseService.getInstance(mContext).existUser(it.id)
                    val rowItem = SearchUserRowRemoteItem(it.id, it.name ?: "", it.avatarUrl, fav)

                    if (it.name != null) {
                        list.add(rowItem)
                    }
                }

                mList.clear()
                mList.addAll(list)

            }
            else -> return
        }
    }
}

