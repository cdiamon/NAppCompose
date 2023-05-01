package com.example.nappcompose.data.networkmodels

import com.google.gson.annotations.SerializedName

data class UsersSearchResponse(
    @SerializedName("items") val userList: List<User>,
    val total_count: Int,
    val incomplete_results: Boolean
) {
    data class User(
        val id: Int,
        val login: String,
        val avatar_url: String,
        val html_url: String,
    )
}
