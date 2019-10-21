package com.eeearl.githubstars.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.eeearl.githubstars.ui.local.SearchUserLocalFragment
import com.eeearl.githubstars.ui.remote.SearchUserRemoteFragment

class SearchUserViewPagerAdapter(
    fragmentManager: FragmentManager
): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 2

        const val REMOTE_FRAGMENT_ID = 0
        const val LOCAL_FRAGMENT_ID = 1
    }

    override fun getItem(i: Int): Fragment {
        return when(i) {
            REMOTE_FRAGMENT_ID -> SearchUserRemoteFragment()
            LOCAL_FRAGMENT_ID -> SearchUserLocalFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            REMOTE_FRAGMENT_ID -> "API"
            LOCAL_FRAGMENT_ID -> "로컬"
            else -> ""
        }
    }
}
