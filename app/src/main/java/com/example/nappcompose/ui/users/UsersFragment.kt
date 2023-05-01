package com.example.nappcompose.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.nappcompose.ui.theme.GHTheme
import com.example.nappcompose.ui.users.views.UsersScreen
//import com.google.accompanist.insets.ExperimentalAnimatedInsets
//import com.google.accompanist.insets.LocalWindowInsets
//import com.google.accompanist.insets.ViewWindowInsetObserver
//import com.google.accompanist.insets.navigationBarsPadding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(), LifecycleOwner {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setContent {
            GHTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.safeContent,
                ) { padding ->
                    Box(
                        modifier = Modifier.padding(padding)
                    ) {
                        UsersScreen()
                    }
                }
            }
        }
    }
}
