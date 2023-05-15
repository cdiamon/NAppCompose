@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.nappcompose.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nappcompose.domain.models.User
import com.example.nappcompose.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface UsersViewModel {

    val searchText: StateFlow<String>
    val userList: Flow<PagingData<User.UserModel>>
    val userDetails: StateFlow<Result<User>>
    fun fetchUserDetails(name: String): Job
    fun onSearchTextChange(text: String)
}

/**
 * Used to communicate between screens.
 */
@OptIn(FlowPreview::class)
@HiltViewModel
class UsersViewModelImpl @Inject constructor(
    private val dataRepository: DataRepository
) : UsersViewModel, ViewModel() {

    override val searchText = MutableStateFlow("")

    override val userList: Flow<PagingData<User.UserModel>> = searchText
        .debounce(DEBOUNCE_MILLIS)
        .flatMapLatest {
            getUsersList(it)
        }.cachedIn(viewModelScope)

    override val userDetails =
        MutableStateFlow<Result<User>>(Result.success(User.Loading))

    private fun getUsersList(query: String): Flow<PagingData<User.UserModel>> =
        dataRepository.loadUsersList(query).cachedIn(viewModelScope)

    override fun onSearchTextChange(text: String) {
        searchText.value = text
    }

    override fun fetchUserDetails(name: String) =
        dataRepository.loadUserDetails(name)
            .onEach {
                userDetails.value = it
            }.onStart {
                userDetails.value = Result.success(User.Loading)
            }.launchIn(viewModelScope)

    private companion object {
        const val DEBOUNCE_MILLIS = 500L
    }
}
