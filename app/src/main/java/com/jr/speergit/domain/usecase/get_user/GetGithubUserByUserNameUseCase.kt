package com.jr.speergit.domain.usecase.get_user

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GetGithubUserByUserNameUseCase {
    suspend fun getGithubUserByUserName(username: String): Flow<NetworkResult<GithubUser>>
}