package com.example.nappcompose.data

import com.example.nappcompose.models.ResultStatus
import com.example.nappcompose.models.requests.BtcChartRequest
import com.example.nappcompose.models.responses.BtcChartResponse

interface BtcDataStore {
    suspend fun getCharts(searchQuery: BtcChartRequest): ResultStatus<BtcChartResponse>
}
