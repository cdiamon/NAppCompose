package com.example.nappcompose.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nappcompose.data.networkmodels.ResultStatus
import com.example.nappcompose.domain.models.UserModel
import com.example.nappcompose.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Used to communicate between screens.
 */
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _userList = MutableStateFlow(PagingData.empty<UserModel.Generic>())
    val userList: StateFlow<PagingData<UserModel.Generic>> = searchText
        .debounce(500L)
        .flatMapMerge {
            getUsersList(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _userList.value
        )

    private val _userDetails =
        MutableStateFlow<ResultStatus<UserModel.Detailed>>(ResultStatus.loading())
    val userDetails = _userDetails.asStateFlow()

    private fun getUsersList(query: String): Flow<PagingData<UserModel.Generic>> =
        dataRepository.loadUsersList(query).cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun fetchUserDetails(name: String) =
        dataRepository.loadUserDatails(name)
            .onEach {
                _userDetails.value = it
            }.launchIn(viewModelScope)
}
