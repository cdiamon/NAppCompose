package com.example.nappcompose.domain.mappers

import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.domain.models.User
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class UserDetailsMapper @Inject constructor() {
    fun map(response: Result<UserDetailsResponse>): Result<User> =
        response.mapCatching { from ->
            User.UserModel(
                id = from.id,
                login = from.login,
                name = from.name,
                location = from.location,
                avatarUrl = from.avatarUrl,
                url = from.url,
                bio = from.bio,
                followers = from.followers,
                publicRepos = from.publicRepos,
                publicGists = from.publicGists,
                createdAt = from.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
            )
        }
}
