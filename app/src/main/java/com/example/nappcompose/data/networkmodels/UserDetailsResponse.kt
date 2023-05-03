package com.example.nappcompose.data.networkmodels

import com.google.gson.annotations.SerializedName
import java.util.Date

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
data class UserDetailsResponse(
    val id: Int,
    val login: String,
    val name: String?,
    val location: String?,
    val url: String,
    val bio: String?,
    val followers: Int?,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("public_repos") val publicRepos: Int,
    @SerializedName("public_gists") val publicGists: Int,
    @SerializedName("created_at") val createdAt: Date,
)
