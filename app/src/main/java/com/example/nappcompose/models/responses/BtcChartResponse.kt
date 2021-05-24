package com.example.nappcompose.models.responses

/**
 * examples:
 * @param status "ok"
 * @param name "Confirmed Transactions Per Day"
 * @param unit "Transactions"
 * @param period "day"
 * @param description "The number of daily confirmed Bitcoin transactions."
 * @param values "values": [ {"x": 1442534400, "y": 188330}, ... ]
 */
data class BtcChartResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<Point>
) {
    data class Point(
        val x: Double,
        val y: Double
    )
}
