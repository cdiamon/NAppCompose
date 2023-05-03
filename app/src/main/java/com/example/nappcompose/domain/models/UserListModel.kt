package com.example.nappcompose.domain.models

sealed class UserModel(
    open val id: Int,
    open val login: String,
    open val avatarUrl: String,
) {

    data class Generic(
        override val id: Int,
        override val login: String,
        override val avatarUrl: String,
        val htmlUrl: String?,
    ) : UserModel(id = id, login = login, avatarUrl = avatarUrl)

    data class Detailed(
        override val id: Int,
        override val login: String,
        override val avatarUrl: String,
        val name: String?,
        val location: String?,
        val url: String,
        val createdAt: String,
        val bio: String?,
        val publicRepos: Int,
        val publicGists: Int,
    ) : UserModel(id = id, login = login, avatarUrl = avatarUrl)
}
