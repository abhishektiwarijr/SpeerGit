package com.jr.speergit.domain.repository

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getUser(username: String): Flow<NetworkResult<GithubUser>>

    suspend fun getUserFollowers(username: String): Flow<NetworkResult<List<GithubUser>>>

    suspend fun getUserFollowing(username: String): Flow<NetworkResult<List<GithubUser>>>
}