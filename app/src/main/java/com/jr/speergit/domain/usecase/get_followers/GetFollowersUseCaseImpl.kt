package com.jr.speergit.domain.usecase.get_followers

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowersUseCaseImpl @Inject constructor(
    private val githubRepository: GithubRepository
) : GetFollowersUseCase {

    override suspend fun getFollowersByUserName(username: String): Flow<NetworkResult<List<GithubUser>>> {
        return githubRepository.getUserFollowers(username = username)
    }
}