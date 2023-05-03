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

    val userList: Flow<PagingData<UserModel.Generic>> = searchText
        .debounce(DEBOUNCE_MILLIS)
        .flatMapLatest {
            getUsersList(it)
        }.cachedIn(viewModelScope)

    private val _userDetails =
        MutableStateFlow<ResultStatus<UserModel.Detailed>>(ResultStatus.loading())
    val userDetails = _userDetails.asStateFlow()

    private fun getUsersList(query: String): Flow<PagingData<UserModel.Generic>> =
        dataRepository.loadUsersList(query).cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun fetchUserDetails(name: String) =
        dataRepository.loadUserDetails(name)
            .onEach {
                _userDetails.value = it
            }.launchIn(viewModelScope)

    private companion object {
        const val DEBOUNCE_MILLIS = 500L
    }
}
