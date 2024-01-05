package com.jr.speergit.domain.usecase.get_user

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGithubUserByUserNameUseCaseImpl @Inject constructor(
    private val githubRepository: GithubRepository
) : GetGithubUserByUserNameUseCase {

    override suspend fun getGithubUserByUserName(username: String): Flow<NetworkResult<GithubUser>> {
        return githubRepository.getUser(username = username)
    }
}