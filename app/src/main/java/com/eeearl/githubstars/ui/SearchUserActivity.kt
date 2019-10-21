package com.eeearl.githubstars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eeearl.githubstars.R
import com.eeearl.githubstars.databinding.ActivitySearchUserBinding

class SearchUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySearchUserBinding = DataBindingUtil.setContentView<ActivitySearchUserBinding>(this, R.layout.activity_search_user)
        val viewPagerAdapter = SearchUserViewPagerAdapter(supportFragmentManager)
        activitySearchUserBinding.tlTabs.setupWithViewPager(activitySearchUserBinding.vpSearchListWrapper)
        activitySearchUserBinding.vpSearchListWrapper.adapter = viewPagerAdapter
    }
}
