package com.jr.speergit.presentation.followerswings

import com.jr.speergit.domain.model.GithubUser

sealed interface FollowersFollowingsUiState {
    object Loading : FollowersFollowingsUiState
    data class Success(val data: List<GithubUser>) : FollowersFollowingsUiState
    data class Error(val error: String? = null) : FollowersFollowingsUiState
}
