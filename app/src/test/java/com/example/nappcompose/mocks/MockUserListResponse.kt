package com.example.nappcompose.mocks

import com.example.nappcompose.data.networkmodels.ResultStatus
import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import com.example.nappcompose.domain.models.UserModel
import java.time.Instant
import java.util.*


fun mockUserListResponse() = ResultStatus(
    status = ResultStatus.Status.SUCCESS,
    data = UsersSearchResponse(
        totalCount = 10,
        incompleteResults = false,
        userList = listOf(
            UsersSearchResponse.User(
                id = 123,
                login = "LoginName",
                avatarUrl = "https://image.png",
                htmlUrl = "https://github.com/LoginName"
            ),
            UsersSearchResponse.User(
                id = 456,
                login = "OtherName",
                avatarUrl = "https://image2.png",
                htmlUrl = "https://github.com/OtherName"
            ),
        )
    )
)

fun mockUserDetailsResponse() = ResultStatus(
    status = ResultStatus.Status.SUCCESS,
    data = UserDetailsResponse(
        id = 123,
        login = "LoginName",
        name = "Sam Black",
        location = "Tokyo",
        avatarUrl = "https://image.png",
        url = "https://github.com/LoginName",
        bio = "I play drums",
        publicRepos = 3,
        publicGists = 2,
        createdAt = Date.from(Instant.parse("2018-10-22T00:37:25Z"))
    )
)

fun mockUserDetailsModel() = ResultStatus(
    status = ResultStatus.Status.SUCCESS,
    data = UserModel.Detailed(
        id = 123,
        login = "LoginName",
        name = "Sam Black",
        location = "Tokyo",
        avatarUrl = "https://image.png",
        url = "https://github.com/LoginName",
        bio = "I play drums",
        publicRepos = 3,
        publicGists = 2,
        createdAt = "22.10.2018"
    )
)
