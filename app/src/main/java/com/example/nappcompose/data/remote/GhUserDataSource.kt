package com.example.nappcompose.data.remote

import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.data.networkmodels.UsersQueryRequest
import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import retrofit2.Response
import javax.inject.Inject

class GhUserDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataStore {

    override suspend fun getUserList(searchQuery: UsersQueryRequest): Result<UsersSearchResponse> =
        getResponse {
            userApi.getUsers(
                query = searchQuery.query,
                page = searchQuery.page,
                perPage = searchQuery.perPage,
            )
        }

    override suspend fun getUserDetails(name: String): Result<UserDetailsResponse> =
        getResponse {
            userApi.getUserDetails(name)
        }

    companion object {
        suspend fun <T> getResponse(request: suspend () -> Response<T>): Result<T> {
            return try {
                val result = request.invoke()
                result.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception(result.errorBody().toString()))
            } catch (e: Throwable) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}
