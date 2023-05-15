package com.example.nappcompose.data.networkmodels

import kotlinx.datetime.Instant
import kotlinx.serialization.*
import java.util.*

/**
 * @param id The id of the user.
 * @param login The username of the user.
 * @param name The name of the user.
 * @param location The location of the user.
 * @param avatarUrl The avatar url of the user.
 * @param url The url of the user.
 * @param bio The biography of the user.
 * @param publicRepos The public repos of the user.
 * @param publicGists The public gists of the user.
 * @param createdAt When the user was created.
 */
@Serializable
data class UserDetailsResponse(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
    @SerialName("name") val name: String?,
    @SerialName("location") val location: String?,
    @SerialName("url") val url: String,
    @SerialName("bio") val bio: String?,
    @SerialName("followers") val followers: Int?,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("public_repos") val publicRepos: Int,
    @SerialName("public_gists") val publicGists: Int,
    @SerialName("created_at") val createdAt: Instant,
)
