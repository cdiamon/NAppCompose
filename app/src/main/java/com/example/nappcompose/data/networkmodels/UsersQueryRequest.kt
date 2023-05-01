package com.example.nappcompose.data.networkmodels


/**
 * URL: https://api.github.com/search/users?q={query}{&page,per_page,sort,order}
 */
data class UsersQueryRequest(
    val query: String,
    val page: Int? = 1,
    val perPage: Int = 10,
)
