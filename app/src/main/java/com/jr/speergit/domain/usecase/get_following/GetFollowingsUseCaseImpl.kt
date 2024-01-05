package com.jr.speergit.domain.usecase.get_following

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowingsUseCaseImpl @Inject constructor(
    private val githubRepository: GithubRepository
) : GetFollowingsUseCase {
    override suspend fun getFollowingsByUserName(username: String): Flow<NetworkResult<List<GithubUser>>> {
        return githubRepository.getUserFollowing(username = username)
    }
}