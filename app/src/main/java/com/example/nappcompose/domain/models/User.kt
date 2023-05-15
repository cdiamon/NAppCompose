package com.example.nappcompose.domain.models

sealed interface User {

    data class UserModel(
        val id: Int,
        val login: String,
        val avatarUrl: String,
        val htmlUrl: String? = null,
        val name: String? = null,
        val location: String? = null,
        val url: String? = null,
        val bio: String? = null,
        val followers: Int? = null,
        val createdAt: String? = null,
        val publicRepos: Int? = null,
        val publicGists: Int? = null,
    ) : User

    object Loading : User

    data class Error(
        val message: String,
    ) : User
}
