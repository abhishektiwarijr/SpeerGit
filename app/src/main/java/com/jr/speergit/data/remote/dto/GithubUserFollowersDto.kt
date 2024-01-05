package com.jr.speergit.data.remote.dto

class GithubUserFollowersDto : ArrayList<GithubUserDto>()

fun GithubUserFollowersDto.toGithubUserFollowers() = this.map {
    it.toGithubUser()
}