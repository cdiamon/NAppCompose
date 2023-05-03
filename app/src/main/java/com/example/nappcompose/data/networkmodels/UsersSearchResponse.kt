package com.example.nappcompose.data.networkmodels

import com.google.gson.annotations.SerializedName

data class UsersSearchResponse(
    @SerializedName("items") val userList: List<User>,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean
) {
    data class User(
        @SerializedName("id") val id: Int,
        @SerializedName("login") val login: String,
        @SerializedName("avatar_url") val avatarUrl: String,
        @SerializedName("html_url") val htmlUrl: String,
    )
}
