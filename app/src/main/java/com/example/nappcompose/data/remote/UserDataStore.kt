package com.example.nappcompose.data.remote

import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.data.networkmodels.UsersQueryRequest
import com.example.nappcompose.data.networkmodels.UsersSearchResponse

interface UserDataStore {

    suspend fun getUserList(searchQuery: UsersQueryRequest): Result<UsersSearchResponse>

    suspend fun getUserDetails(name: String): Result<UserDetailsResponse>
}
