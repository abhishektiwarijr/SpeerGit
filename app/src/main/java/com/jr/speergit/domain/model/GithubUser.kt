package com.jr.speergit.domain.model

data class GithubUser(
    val id                : Long,
    val avatarUrl         : String,
    val username          : String,
    val name              : String,
    val description       : String,
    val repositories      : Int,
    val followers         : Int,
    val following         : Int,
)