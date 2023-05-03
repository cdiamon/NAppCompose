package com.example.nappcompose.domain.mappers

import android.icu.text.SimpleDateFormat
import com.example.nappcompose.data.networkmodels.ResultStatus
import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.domain.models.UserModel
import java.util.*
import javax.inject.Inject

class UserDetailsMapper @Inject constructor() {
    fun map(usersDetailsResponse: ResultStatus<UserDetailsResponse>): ResultStatus<UserModel.Detailed> =
        ResultStatus(
            status = usersDetailsResponse.status,
            data = usersDetailsResponse.data?.let {

                val dataString =
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it.createdAt)

                UserModel.Detailed(
                    id = it.id,
                    login = it.login,
                    name = it.name,
                    location = it.location,
                    avatarUrl = it.avatarUrl,
                    url = it.url,
                    bio = it.bio,
                    followers = it.followers,
                    publicRepos = it.publicRepos,
                    publicGists = it.publicGists,
                    createdAt = dataString
                )
            },
            error = usersDetailsResponse.error,
            message = usersDetailsResponse.message
        )
}
