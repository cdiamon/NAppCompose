package com.example.nappcompose.data.networkmodels

import com.google.gson.annotations.SerializedName

data class UsersSearchResponse(
    @SerializedName("items") val userList: List<User>,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean
) {
    data class User(
        val id: Int,
        val login: String,
        @SerializedName("avatar_url") val avatarUrl: String,
        @SerializedName("html_url") val htmlUrl: String,
    )
}
