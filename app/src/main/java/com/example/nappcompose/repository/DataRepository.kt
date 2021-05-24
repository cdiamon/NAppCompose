package com.example.nappcompose.repository

import com.example.nappcompose.data.BtcDataStore
import com.example.nappcompose.models.ResultStatus
import com.example.nappcompose.models.requests.BtcChartRequest
import com.example.nappcompose.models.responses.BtcChartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface DataRepository {
    fun loadBtcChart(chartRequest: BtcChartRequest): Flow<ResultStatus<BtcChartResponse>>
}

class DataRepositoryImpl(val dataSource: BtcDataStore) : DataRepository {

    override fun loadBtcChart(chartRequest: BtcChartRequest): Flow<ResultStatus<BtcChartResponse>> {
        return flow {
            emit(ResultStatus.loading())
            emit(dataSource.getCharts(chartRequest))
        }.flowOn(Dispatchers.IO)
    }
}
