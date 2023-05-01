package com.example.nappcompose.data.remote

import com.example.nappcompose.data.UserDataStore
import com.example.nappcompose.data.networkmodels.ResultStatus
import com.example.nappcompose.data.networkmodels.UsersQueryRequest
import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import retrofit2.Response
import javax.inject.Inject

class GhUserDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataStore {

    override suspend fun getUserList(searchQuery: UsersQueryRequest): ResultStatus<UsersSearchResponse> =
        getResponse {
            userApi.getUsers(
                query = searchQuery.query,
                page = searchQuery.page,
                perPage = searchQuery.perPage,
            )
        }

    override suspend fun getUserDetails(name: String): ResultStatus<UserDetailsResponse> =
        getResponse {
            userApi.getUserDetails(name)
        }

    companion object {
        suspend fun <T> getResponse(request: suspend () -> Response<T>): ResultStatus<T> {
            return try {
                val result = request.invoke()
                if (result.isSuccessful) {
                    return ResultStatus.success(result.body())
                } else {
                    ResultStatus.error(result.errorBody().toString())
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                ResultStatus.error("Unknown Error", null)
            }
        }
    }
}
