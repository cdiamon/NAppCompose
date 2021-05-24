package com.example.nappcompose.ui.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.nappcompose.models.ResultStatus
import com.example.nappcompose.models.ResultStatus.Status.LOADING
import com.example.nappcompose.models.responses.BtcChartResponse
import com.example.chartlibrary.tehrasChartsLine.LineChart
import com.example.chartlibrary.tehrasChartsLine.LineChartData
import com.example.chartlibrary.tehrasChartsLine.animation.simpleChartAnimation
import com.example.chartlibrary.tehrasChartsLine.renderer.point.NoPointDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.xaxis.SimpleXAxisDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.line.SolidLineDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.yaxis.SimpleYAxisDrawer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Entry point for a chart screen.
 *
 * @param buttonState [ButtonUiState] that contains button info to display
 * @param flow has state for charts
 * @param swapFilters used to handle button clicks
 * @param modifier [Modifier] to apply to this layout node
 */
@Composable
fun ListContent(
    buttonState: ButtonUiState,
    flow: Flow<ResultStatus<BtcChartResponse>>,
    swapFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val stateFlow by flow.collectAsState(
        initial = ResultStatus(LOADING)
    )

    Surface(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.secondary)
                .alpha(if (stateFlow.status == LOADING) 0.3f else 1f)
        ) {
            stateFlow.data?.values?.let {
                MyLineChart(it)

                BottomButton(
                    state = buttonState,
                    swapFilters = swapFilters,
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (stateFlow.status == LOADING) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.secondary)
                            .padding(64.dp),
                        color = MaterialTheme.colors.onSecondary,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun MyLineChart(values: List<BtcChartResponse.Point>) {
    val lineChartData = LineChartData(
        points = values
            .map {
                LineChartData.Point(it.y.toFloat(), it.y.toString())
            }
    )

    LineChart(
        lineChartData = lineChartData,
        modifier = Modifier
            .fillMaxSize(),
        animation = simpleChartAnimation(durationMillis = 2500),
        pointDrawer = NoPointDrawer,
        lineDrawer = SolidLineDrawer(
            thickness = 1.dp,
            color = MaterialTheme.colors.onSecondary
        ),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        horizontalOffset = 5f
    )
}

@Composable
fun BottomButton(
    state: ButtonUiState,
    swapFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        SwitchFilterBottom(
            filterText = state.filterName,
            onClicked = {
                scope.launch {
                    swapFilters.invoke()
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
