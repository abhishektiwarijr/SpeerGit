package com.jr.speergit.data.repository

import com.jr.speergit.common.NetworkResult
import com.jr.speergit.data.remote.GithubApi
import com.jr.speergit.data.remote.dto.toGithubUser
import com.jr.speergit.data.remote.dto.toGithubUserFollowers
import com.jr.speergit.data.remote.dto.toGithubUserFollowings
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.domain.repository.GithubRepository
import com.jr.virtusatest.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GithubRepository {

    override suspend fun getUser(user: String): Flow<NetworkResult<GithubUser>> = flow {
        try {
            val response = githubApi.getGithubUser(user)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(NetworkResult.Success(body.toGithubUser()))
            } else {
                emit(NetworkResult.Error(code = response.code(), message = response.message()))
            }
        } catch (e: HttpException) {
            emit(NetworkResult.Error(code = e.code(), message = e.message()))
        } catch (e: Throwable) {
            emit(NetworkResult.Exception(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getUserFollowers(username: String): Flow<NetworkResult<List<GithubUser>>> =
        flow {
            try {
                val response = githubApi.getGithubUserFollowers(username)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(NetworkResult.Success(body.toGithubUserFollowers()))
                } else {
                    emit(NetworkResult.Error(code = response.code(), message = response.message()))
                }
            } catch (e: HttpException) {
                emit(NetworkResult.Error(code = e.code(), message = e.message()))
            } catch (e: Throwable) {
                emit(NetworkResult.Exception(e))
            }
        }.flowOn(ioDispatcher)

    override suspend fun getUserFollowing(username: String): Flow<NetworkResult<List<GithubUser>>> =
        flow {
            try {
                val response = githubApi.getGithubUserFollowings(username)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(NetworkResult.Success(body.toGithubUserFollowings()))
                } else {
                    emit(NetworkResult.Error(code = response.code(), message = response.message()))
                }
            } catch (e: HttpException) {
                emit(NetworkResult.Error(code = e.code(), message = e.message()))
            } catch (e: Throwable) {
                emit(NetworkResult.Exception(e))
            }
        }.flowOn(ioDispatcher)
}
