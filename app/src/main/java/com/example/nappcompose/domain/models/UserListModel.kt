package com.example.nappcompose.domain.models

sealed class UserModel(
    open val id: Int,
    open val login: String,
    open val avatar_url: String,
) {

    data class Generic(
        override val id: Int,
        override val login: String,
        override val avatar_url: String,
        val htmlUrl: String?,
    ) : UserModel(id = id, login = login, avatar_url = avatar_url)

    data class Detailed(
        override val id: Int,
        override val login: String,
        override val avatar_url: String,
        val name: String?,
        val location: String?,
        val url: String,
        val createdAt: String,
        val bio: String?,
        val public_repos: Int,
        val public_gists: Int,
    ) : UserModel(id = id, login = login, avatar_url = avatar_url)
}
