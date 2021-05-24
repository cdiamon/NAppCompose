package com.example.nappcompose.models.requests


/**
 * URL:
 * https://api.blockchain.info/charts/$chartName?timespan=$timespan&rollingAverage=$rollingAverage
 *   &start=$start&format=$format&sampled=$sampled
 * Method: GET
 *
 * Example:
 * https://api.blockchain.info/charts/transactions-per-second?timespan=5weeks&rollingAverage=8hours&format=json
 *
 * $timespan - Duration of the chart, default is 1 year for most charts, 1 week for mempool charts. (Optional)
 * $rollingAverage - Duration over which the data should be averaged. (Optional)
 * $start - Datetime at which to start the chart. (Optional)
 * $format - Either JSON or CSV, defaults to JSON. (Optional)
 * $sampled - Boolean set to 'true' or 'false' (default 'true'). If true, limits the number of
 *   datapoints returned to ~1.5k for performance reasons. (Optional)
 */
data class BtcChartRequest(
    val timespan: String? = null,
    val rollingAverage: String? = null,
    val start: Long? = null,
    val format: String = "json",
    val sampled: Boolean = true
)
