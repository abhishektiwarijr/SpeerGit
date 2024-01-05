package com.jr.speergit.data.remote

import com.jr.speergit.data.remote.dto.GithubUserDto
import com.jr.speergit.data.remote.dto.GithubUserFollowersDto
import com.jr.speergit.data.remote.dto.GithubUserFollowingDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{user}")
    suspend fun getGithubUser(@Path("user") user: String): Response<GithubUserDto>

    @GET("users/{user}/followers")
    suspend fun getGithubUserFollowers(
        @Path("user") user: String
    ): Response<GithubUserFollowersDto>

    @GET("users/{user}/following")
    suspend fun getGithubUserFollowings(
        @Path("user") user: String
    ): Response<GithubUserFollowingDto>
}