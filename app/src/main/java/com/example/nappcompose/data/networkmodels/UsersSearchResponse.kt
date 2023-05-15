package com.example.nappcompose.data.networkmodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersSearchResponse(
    @SerialName("items") val userList: List<User>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean
) {
    @Serializable
    data class User(
        @SerialName("id") val id: Int,
        @SerialName("login") val login: String,
        @SerialName("avatar_url") val avatarUrl: String,
        @SerialName("html_url") val htmlUrl: String,
    )
}
