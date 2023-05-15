package com.example.nappcompose.domain.mappers

import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import com.example.nappcompose.domain.models.User
import javax.inject.Inject

class UserListMapper @Inject constructor() {
    fun map(searchResponseUser: UsersSearchResponse.User?): User.UserModel? =
        searchResponseUser?.let {
            User.UserModel(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
            )
        }
}
