package com.example.chartlibrary.tehrasChartsLine

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculateDrawableArea
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculateFillPath
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculateLinePath
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculatePointLocation
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculateXAxisDrawableArea
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculateXAxisLabelsDrawableArea
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.calculateYAxisDrawableArea
import com.example.chartlibrary.tehrasChartsLine.LineChartUtils.withProgress
import com.example.chartlibrary.tehrasChartsLine.animation.simpleChartAnimation
import com.example.chartlibrary.tehrasChartsLine.renderer.line.LineDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.line.LineShader
import com.example.chartlibrary.tehrasChartsLine.renderer.line.NoLineShader
import com.example.chartlibrary.tehrasChartsLine.renderer.line.SolidLineDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.point.FilledCircularPointDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.point.PointDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.xaxis.SimpleXAxisDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.xaxis.XAxisDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.yaxis.SimpleYAxisDrawer
import com.example.chartlibrary.tehrasChartsLine.renderer.yaxis.YAxisDrawer

@Composable
fun LineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: PointDrawer = FilledCircularPointDrawer(),
    lineDrawer: LineDrawer = SolidLineDrawer(),
    lineShader: LineShader = NoLineShader,
    xAxisDrawer: XAxisDrawer = SimpleXAxisDrawer(),
    yAxisDrawer: YAxisDrawer = SimpleYAxisDrawer(),
    horizontalOffset: Float = 5f
) {
    check(horizontalOffset in 0f..25f) {
        "Horizontal offset is the % offset from sides, " +
                "and should be between 0%-25%"
    }

    val transitionAnimation = remember(lineChartData.points) { Animatable(initialValue = 0f) }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0f)
        transitionAnimation.animateTo(1f, animationSpec = animation)
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = calculateYAxisDrawableArea(
                xAxisLabelSize = xAxisDrawer.requiredHeight(this),
                size = size
            )
            val xAxisDrawableArea = calculateXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = xAxisDrawer.requiredHeight(this),
                size = size
            )
            val xAxisLabelsDrawableArea = calculateXAxisLabelsDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                offset = horizontalOffset
            )
            val chartDrawableArea = calculateDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            )

            // Draw the chart line.
            lineDrawer.drawLine(
                drawScope = this,
                canvas = canvas,
                linePath = calculateLinePath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )

            lineShader.fillLine(
                drawScope = this,
                canvas = canvas,
                fillPath = calculateFillPath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )

            lineChartData.points.forEachIndexed { index, point ->
                withProgress(
                    index = index,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                ) {
                    pointDrawer.drawPoint(
                        drawScope = this,
                        canvas = canvas,
                        center = calculatePointLocation(
                            drawableArea = chartDrawableArea,
                            lineChartData = lineChartData,
                            point = point,
                            index = index
                        )
                    )
                }
            }

            // Draw the X Axis line.
            xAxisDrawer.drawAxisLine(
                drawScope = this,
                drawableArea = xAxisDrawableArea,
                canvas = canvas
            )

            xAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = xAxisLabelsDrawableArea,
                labels = lineChartData.points.map { it.label }
            )

            // Draw the Y Axis line.
            yAxisDrawer.drawAxisLine(
                drawScope = this,
                canvas = canvas,
                drawableArea = yAxisDrawableArea
            )

            yAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = yAxisDrawableArea,
                minValue = lineChartData.minYValue,
                maxValue = lineChartData.maxYValue
            )
        }
    }
}
