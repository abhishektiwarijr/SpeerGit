package com.jr.speergit.presentation.home

import com.jr.speergit.domain.model.GithubUser

sealed interface HomeScreenUiState {
    object Empty : HomeScreenUiState
    object Loading : HomeScreenUiState
    data class Success(val data: GithubUser) : HomeScreenUiState
    data class Error(val error: String? = null) : HomeScreenUiState
}