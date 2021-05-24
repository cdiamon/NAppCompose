package com.example.nappcompose.data.remote

import com.example.nappcompose.data.BtcDataStore
import com.example.nappcompose.models.ResultStatus
import com.example.nappcompose.models.requests.BtcChartRequest
import com.example.nappcompose.models.responses.BtcChartResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteBtcDataSource @Inject constructor(val btcApi: BtcApi) : BtcDataStore {

    override suspend fun getCharts(searchQuery: BtcChartRequest): ResultStatus<BtcChartResponse> {
        return getResponse {
            btcApi.getCharts(
                timespan = searchQuery.timespan,
                rollingAverage = searchQuery.rollingAverage,
                start = searchQuery.start,
                format = searchQuery.format,
                sampled = searchQuery.sampled,
            )
        }
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>): ResultStatus<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return ResultStatus.success(result.body())
            } else {
                ResultStatus.error(result.errorBody().toString())
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            ResultStatus.error("Unknown Error", null)
        }
    }
}
