package com.example.nappcompose.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nappcompose.data.networkmodels.ResultStatus
import com.example.nappcompose.data.remote.UserDataStore
import com.example.nappcompose.domain.UsersPagingSource
import com.example.nappcompose.domain.mappers.UserDetailsMapper
import com.example.nappcompose.domain.mappers.UserListMapper
import com.example.nappcompose.domain.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface DataRepository {
    fun loadUserDetails(name: String): Flow<ResultStatus<UserModel.Detailed>>
    fun loadUsersList(query: String): Flow<PagingData<UserModel.Generic>>
}

class DataRepositoryImpl @Inject constructor(
    private val dataSource: UserDataStore,
    private val listMapper: UserListMapper,
    private val detailsMapper: UserDetailsMapper,
) : DataRepository {

    override fun loadUsersList(query: String): Flow<PagingData<UserModel.Generic>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                UsersPagingSource(dataSource, query, listMapper)
            }
        ).flow
    }

    override fun loadUserDetails(name: String): Flow<ResultStatus<UserModel.Detailed>> =
        flow {
            val response = dataSource.getUserDetails(name)
            emit(detailsMapper.map(response))
        }
}
