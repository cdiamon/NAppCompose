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
        total_count = 10,
        incomplete_results = false,
        userList = listOf(
            UsersSearchResponse.User(
                id = 123,
                login = "LoginName",
                avatar_url = "https://image.png",
                html_url = "https://github.com/LoginName"
            ),
            UsersSearchResponse.User(
                id = 456,
                login = "OtherName",
                avatar_url = "https://image2.png",
                html_url = "https://github.com/OtherName"
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
        avatar_url = "https://image.png",
        url = "https://github.com/LoginName",
        bio = "I play drums",
        public_repos = 3,
        public_gists = 2,
        created_at = Date.from(Instant.parse("2018-10-22T00:37:25Z"))
    )
)

fun mockUserDetailsModel() = ResultStatus(
    status = ResultStatus.Status.SUCCESS,
    data = UserModel.Detailed(
        id = 123,
        login = "LoginName",
        name = "Sam Black",
        location = "Tokyo",
        avatar_url = "https://image.png",
        url = "https://github.com/LoginName",
        bio = "I play drums",
        public_repos = 3,
        public_gists = 2,
        createdAt = "22.10.2018"
    )
)
