package com.example.nappcompose.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.nappcompose.ui.theme.ChartsTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.google.accompanist.insets.navigationBarsPadding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : Fragment(), LifecycleOwner {

    private val chartsViewModel: ChartViewModel by activityViewModels()

    @OptIn(ExperimentalAnimatedInsets::class) // Opt-in to experiment animated insets support
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        val windowInsets = ViewWindowInsetObserver(this)
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            CompositionLocalProvider(
                LocalWindowInsets provides windowInsets,
            ) {
                ChartsTheme {
                    ListContent(
                        buttonState = chartsViewModel.getButtonState(),
                        flow = chartsViewModel.fetchChartData(),
                        swapFilters = {
                            chartsViewModel.swapCurrChartsFilter()
                        },
                        // Add padding so that we are inset from any left/right navigation bars
                        // (usually shown when in landscape orientation)
                        modifier = Modifier.navigationBarsPadding(bottom = false)
                    )
                }
            }
        }
    }
}
