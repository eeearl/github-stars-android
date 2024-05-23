package com.eeearl.githubstars.ui.local

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.eeearl.githubstars.component.SearchLocalUserScreen
import com.eeearl.githubstars.db.DatabaseService

class SearchUserLocalFragment : Fragment() {

    private lateinit var viewModel: SearchUserLocalViewModel
    private lateinit var database: DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DatabaseService.getInstance(activity as Context)
        viewModel = SearchUserLocalViewModel(database)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Surface {
                        SearchLocalUserScreen(
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
}
