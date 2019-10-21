package com.eeearl.githubstars.ui.remote

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.eeearl.githubstars.R
import com.eeearl.githubstars.databinding.FragmentSearchUserRemoteBinding
import com.eeearl.githubstars.net.ApiClient
import com.eeearl.githubstars.net.model.SearchUserRequestData
import com.eeearl.githubstars.ui.SearchUserListAdapter
import com.eeearl.githubstars.util.hideKeyboard

class SearchUserRemoteFragment: Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSearchUserRemoteBinding
    private lateinit var viewModel: SearchUserRemoteViewModel
    private lateinit var adapter: SearchUserListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_user_remote, container, false)

        viewModel = ViewModelProviders.of(this).get(SearchUserRemoteViewModel::class.java)
        viewModel.mContext = inflater.context
        binding.viewModel = viewModel

        adapter = SearchUserListAdapter(arrayListOf())
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvSearchList.adapter = adapter
        binding.rvSearchList.addItemDecoration(divider)
        binding.btnSearchBar.setOnClickListener(this)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        adapter.refreshList(activity as Context)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_search_bar) {
            val searchText: String  = if (viewModel.mSearchText.get() != null) viewModel.mSearchText.get()!! else ""
            val req = SearchUserRequestData(searchText, "desc", 1, 100)
            ApiClient.searchUserInGithub(req, viewModel)

            binding.txtSearchBar.editableText.clear()
            binding.txtSearchBar.clearFocus()
            binding.txtSearchBar.hideKeyboard()
        }
    }
}
