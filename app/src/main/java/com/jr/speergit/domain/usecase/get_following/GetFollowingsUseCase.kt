package com.jr.speergit.domain.usecase.get_following

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GetFollowingsUseCase {
    suspend fun getFollowingsByUserName(username: String): Flow<NetworkResult<List<GithubUser>>>
}