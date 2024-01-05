package com.jr.speergit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jr.speergit.common.NetworkResult
import com.jr.speergit.domain.usecase.get_user.GetGithubUserByUserNameUseCase
import com.jr.speergit.presentation.profile.ProfileUiState
import com.jr.virtusatest.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGithubUserByNameUseCase: GetGithubUserByUserNameUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _homeUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Empty)
    val githubUser: StateFlow<HomeScreenUiState> = _searchText
        .debounce(300)
        .distinctUntilChanged()
        .onEach { _isSearching.update { true } }
        .flatMapLatest { query ->
            if (query.isBlank()) {
                _homeUiState.value = HomeScreenUiState.Empty
                return@flatMapLatest _homeUiState
            }
            getGithubUserByNameUseCase.getGithubUserByUserName(query).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _homeUiState.value = HomeScreenUiState.Error(result.message)
                    }

                    is NetworkResult.Exception -> {
                        _homeUiState.value = HomeScreenUiState.Error(result.e.localizedMessage)
                    }

                    is NetworkResult.Success -> {
                        _homeUiState.value = HomeScreenUiState.Success(data = result.data)
                    }
                }
            }
            return@flatMapLatest _homeUiState
        }
        .onEach { _isSearching.update { false } }
        .flowOn(ioDispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenUiState.Loading)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private val _userResponse: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState(true, null, false))
    val user get() = _userResponse

    fun getGithubUser(username: String) {
        viewModelScope.launch {
            _userResponse.emit(ProfileUiState(true, null, false))
            getGithubUserByNameUseCase.getGithubUserByUserName(username).collectLatest {result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _userResponse.emit(ProfileUiState(false, result.data, false))
                    }
                    is NetworkResult.Error -> {
                        _userResponse.emit(ProfileUiState(false, null, true))
                    }
                    is NetworkResult.Exception -> {
                        _userResponse.emit(ProfileUiState(false, null, true))
                    }
                }
            }
        }
    }
}


