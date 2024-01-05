package com.jr.speergit.presentation.profile

import com.jr.speergit.domain.model.GithubUser

data class ProfileUiState(
    val isLoading: Boolean = false,
    val data: GithubUser? = null,
    val error: Boolean = false,
    val searchString: String = ""
)