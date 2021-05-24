package com.example.nappcompose.data.remote

import com.example.nappcompose.models.responses.BtcChartResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BtcApi {

    @GET("/charts/transactions-per-second/")
    suspend fun getCharts(
        @Query("timespan") timespan: String?,
        @Query("rollingAverage") rollingAverage: String?,
        @Query("start") start: Long?,
        @Query("format") format: String,
        @Query("sampled") sampled: Boolean
    ): Response<BtcChartResponse>
}
