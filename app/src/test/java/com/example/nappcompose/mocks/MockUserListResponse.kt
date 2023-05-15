package com.example.nappcompose.mocks

import com.example.nappcompose.data.networkmodels.UserDetailsResponse
import com.example.nappcompose.data.networkmodels.UsersSearchResponse
import com.example.nappcompose.domain.models.User
import kotlinx.datetime.Instant


fun mockUserListResponse() = Result.success(
    UsersSearchResponse(
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

fun mockUserDetailsResponse() = Result.success(
    UserDetailsResponse(
        id = 123,
        login = "LoginName",
        name = "Sam Black",
        location = "Tokyo",
        avatarUrl = "https://image.png",
        url = "https://github.com/LoginName",
        bio = "I play drums",
        followers = 42,
        publicRepos = 3,
        publicGists = 2,
        createdAt = Instant.parse("2018-10-22T00:37:25Z")
    )
)

fun mockUserDetailsModel() = Result.success(
    User.UserModel(
        id = 123,
        login = "LoginName",
        avatarUrl = "https://image.png",
        name = "Sam Black",
        location = "Tokyo",
        url = "https://github.com/LoginName",
        createdAt = "2018-10-22",
        bio = "I play drums",
        followers = 42,
        publicRepos = 3,
        publicGists = 2,
    )
)
