package com.jr.speergit.di

import com.jr.speergit.common.Constants
import com.jr.speergit.data.remote.GithubApi
import com.jr.speergit.data.repository.GithubRepositoryImpl
import com.jr.speergit.domain.repository.GithubRepository
import com.jr.speergit.domain.usecase.get_followers.GetFollowersUseCase
import com.jr.speergit.domain.usecase.get_followers.GetFollowersUseCaseImpl
import com.jr.speergit.domain.usecase.get_following.GetFollowingsUseCase
import com.jr.speergit.domain.usecase.get_following.GetFollowingsUseCaseImpl
import com.jr.speergit.domain.usecase.get_user.GetGithubUserByUserNameUseCase
import com.jr.speergit.domain.usecase.get_user.GetGithubUserByUserNameUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DataLayerModule {
        @Binds
        abstract fun bindGithubRepository(
            githubRepositoryImpl: GithubRepositoryImpl
        ): GithubRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DomainLayerModule {
        @Binds
        abstract fun bindGetGithubUserByUserName(
            getGithubUserByUserNameUseCase: GetGithubUserByUserNameUseCaseImpl
        ): GetGithubUserByUserNameUseCase

        @Binds
        abstract fun bindGetFollowersUseCase(
            getFollowersUseCase: GetFollowersUseCaseImpl
        ): GetFollowersUseCase

        @Binds
        abstract fun bindGetFollowingsUseCase(
            getFollowingsUseCase: GetFollowingsUseCaseImpl
        ): GetFollowingsUseCase
    }
}