package com.eeearl.githubstars.ui.local

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
import com.eeearl.githubstars.databinding.FragmentSearchUserLocalBinding
import com.eeearl.githubstars.db.DatabaseService
import com.eeearl.githubstars.ui.SearchUserListAdapter
import com.eeearl.githubstars.util.hideKeyboard

class SearchUserLocalFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSearchUserLocalBinding
    private lateinit var viewModel: SearchUserLocalViewModel
    private lateinit var database: DatabaseService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_user_local, container, false)
        viewModel = ViewModelProviders.of(this).get(SearchUserLocalViewModel::class.java)
        database = DatabaseService.getInstance(activity as Context)
        binding.viewModel = viewModel

        val adapter = SearchUserListAdapter(arrayListOf())
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvSearchList.adapter = adapter
        binding.rvSearchList.addItemDecoration(divider)
        binding.btnSearchBar.setOnClickListener(this)

        searchLocalUsers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        searchLocalUsers()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_search_bar) {
            searchLocalUsers()
            binding.txtSearchBar.clearFocus()
            binding.txtSearchBar.hideKeyboard()
        }
    }

    private fun searchLocalUsers() {
        val searchText: String  = viewModel.mSearchText.get() ?: ""
        database.selectGithubUsers(searchText, viewModel)
    }
}
