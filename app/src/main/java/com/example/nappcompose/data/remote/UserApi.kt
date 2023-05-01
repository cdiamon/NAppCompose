package com.example.nappcompose.data.remote

import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    /**
     * Find users via various criteria. This method returns up to 100 results per page
     * @param query The query contains one or more search keywords and qualifiers
     * @param perPage The number of results per page (max 100). Default: 30
     * @param page Page number of the results to fetch. Default: 1
     */
    @GET("/search/users")
    suspend fun getUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int?,
        @Query("page") page: Int?,
    ): Response<UsersSearchResponse>

    /**
     * Provides publicly available information about someone with a GitHub account.
     * @param username login name of the user
     */
    @GET("/users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String,
    ): Response<UserDetailsResponse>
}
