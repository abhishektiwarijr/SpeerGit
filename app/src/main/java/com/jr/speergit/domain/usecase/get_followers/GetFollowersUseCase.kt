package com.jr.speergit.domain.usecase.get_followers

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GetFollowersUseCase {
    suspend fun getFollowersByUserName(username: String): Flow<NetworkResult<List<GithubUser>>>
}