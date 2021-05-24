package com.example.nappcompose.ui.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nappcompose.models.ResultStatus
import com.example.nappcompose.models.requests.BtcChartRequest
import com.example.nappcompose.models.responses.BtcChartResponse
import com.example.nappcompose.repository.DataRepository
import com.example.nappcompose.ui.chart.ChartViewModel.SortTypes.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to communicate between screens.
 */
@HiltViewModel
class ChartViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private var sortType = Default

    private val _currentFilterName = MutableLiveData<String>()
    val currentFilterName: LiveData<String> = _currentFilterName

    // TODO switch to StateFlow to be able to push different data by pressing button
    fun fetchChartData(): Flow<ResultStatus<BtcChartResponse>> {
        return dataRepository.loadBtcChart(
            BtcChartRequest(
                timespan = "1week",
                rollingAverage = "8hours"
            )
        )
    }

    fun getButtonState(): ButtonUiState {
        _currentFilterName.value = ""
        return ButtonUiState(
            filterName = currentFilterName,
        )
    }

    fun swapCurrChartsFilter() {
        when (sortType) {
            Default -> {
                sortType = Hour
                _currentFilterName.value = " (Hour)"
            }
            Hour -> {
                sortType = Day
                _currentFilterName.value = " (Day)"
            }
            Day -> {
                sortType = Week
                _currentFilterName.value = " (Week)"
            }
            Week -> {
                sortType = Hour
                _currentFilterName.value = " (Hour)"
            }
        }
    }

    enum class SortTypes {
        Default, Hour, Day, Week
    }
}
