package com.eeearl.githubstars.ui

import android.content.Context
import android.gesture.Prediction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import com.eeearl.githubstars.R
import com.eeearl.githubstars.component.SearchLocalUserScreen
import com.eeearl.githubstars.component.SearchRemoteUserScreen
import com.eeearl.githubstars.databinding.ActivitySearchUserBinding
import com.eeearl.githubstars.db.DatabaseService
import com.eeearl.githubstars.ui.local.SearchUserLocalViewModel
import com.eeearl.githubstars.ui.remote.SearchUserRemoteViewModel
import kotlinx.coroutines.launch

class SearchUserActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchUserRemoteViewModel
    private lateinit var viewModel2: SearchUserLocalViewModel
    private lateinit var database: DatabaseService

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DatabaseService.getInstance(this)
        viewModel = SearchUserRemoteViewModel(database)
        viewModel2 = SearchUserLocalViewModel(database)

        setContentView(ComposeView(this).apply {
            setViewCompositionStrategy(androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Surface {
                        val pagerState = rememberPagerState(pageCount = {2})
                        val tabs = listOf("Remote", "Local")
                        val coroutineScope = rememberCoroutineScope()
                        Column(modifier = Modifier.fillMaxSize()) {
                            TabRow(
                                selectedTabIndex = pagerState.currentPage,
                                indicator = { tabPositions ->
                                    TabRowDefaults.Indicator(
                                        color = MaterialTheme.colors.primary,
                                        height = 2.dp,
                                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                    )
                                }
                            ) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = pagerState.currentPage == index,
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(index)
                                            }
                                        },
                                        selectedContentColor = MaterialTheme.colors.secondary,
                                        unselectedContentColor = MaterialTheme.colors.onSurface,
                                        text = {
                                            Text(
                                                text = title,
                                                color = if (pagerState.currentPage == index) Color.White else MaterialTheme.colors.secondaryVariant,
                                                fontSize = 14.sp,
                                            )
                                        })
                                }
                            }
                            HorizontalPager(state = pagerState) { page ->
                                when (page) {
                                    0 -> SearchRemoteUserScreen(viewModel = viewModel)
                                    1 -> SearchLocalUserScreen(viewModel = viewModel2)
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}
