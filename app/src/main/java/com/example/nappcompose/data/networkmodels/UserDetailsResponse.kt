package com.example.nappcompose.data.networkmodels

import java.util.Date

/**
 * @param id The id of the user.
 * @param login The username of the user.
 * @param name The name of the user.
 * @param location The location of the user.
 * @param avatar_url The avatar url of the user.
 * @param url The url of the user.
 * @param bio The biography of the user.
 * @param public_repos The public repos of the user.
 * @param public_gists The public gists of the user.
 * @param created_at When the user was created.
 */
data class UserDetailsResponse(
    val id: Int,
    val login: String,
    val name: String?,
    val location: String?,
    val avatar_url: String,
    val url: String,
    val bio: String?,
    val public_repos: Int,
    val public_gists: Int,
    val created_at: Date,
)
