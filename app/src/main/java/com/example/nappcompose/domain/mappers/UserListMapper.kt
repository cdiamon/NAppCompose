package com.example.nappcompose.domain.mappers

import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import com.example.nappcompose.domain.models.UserModel
import javax.inject.Inject

class UserListMapper @Inject constructor() {
    fun map(searchResponseUser: UsersSearchResponse.User?): UserModel.Generic? =
        searchResponseUser?.let {
            UserModel.Generic(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
            )
        }
}
