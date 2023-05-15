package com.example.nappcompose.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nappcompose.data.networkmodels.UsersQueryRequest
import com.example.nappcompose.data.remote.UserDataStore
import com.example.nappcompose.domain.mappers.UserListMapper
import com.example.nappcompose.domain.models.User

class UsersPagingSource(
    private val userApiService: UserDataStore,
    private val query: String,
    private val mapper: UserListMapper
) : PagingSource<Int, User.UserModel>() {
    override fun getRefreshKey(state: PagingState<Int, User.UserModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User.UserModel> {
        return try {
            val page = params.key ?: 1
            val response = if (query.isEmpty()) {
                emptyList()
            } else {
                userApiService.getUserList(
                    UsersQueryRequest(
                        page = page,
                        perPage = params.loadSize,
                        query = query
                    )
                ).getOrNull()?.userList?.mapNotNull {
                    mapper.map(it)
                } ?: emptyList()
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}