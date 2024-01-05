package com.jr.speergit.presentation.followerswings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jr.speergit.common.capitalizeFirstLetter
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.presentation.home.ErrorMessageView
import com.jr.speergit.presentation.home.LoadingView
import com.jr.speergit.presentation.home.NotFoundView
import com.jr.speergit.presentation.home.ProfileCard
import com.jr.speergit.presentation.navigation.Screens
import com.jr.speergit.presentation.profile.TopBar
import com.jr.speergit.presentation.ui.theme.AppTheme

@Composable
fun FollowersFollowingsScreen(
    modifier: Modifier = Modifier,
    username: String,
    title: String,
    navController: NavHostController,
    viewModel: FollowersFollowingsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = null, block = {
        viewModel.getFollowersFollowings(username, title)
    })
    val uiState by viewModel.usersListState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is FollowersFollowingsUiState.Loading -> {
                LoadingView()
            }

            is FollowersFollowingsUiState.Error -> {
                ErrorMessageView(error = (uiState as FollowersFollowingsUiState.Error).error)
            }

            is FollowersFollowingsUiState.Success -> {
                val titleText = remember {
                    title.capitalizeFirstLetter()
                }
                val users = (uiState as FollowersFollowingsUiState.Success).data
                val notFoundText = remember {
                    mutableStateOf("No $title found")
                }
                if (users.isEmpty()) {
                    NotFoundView(text = notFoundText.value)
                } else {
                    TopBar(
                        name = titleText,
                        modifier = Modifier
                    ) { navController.navigateUp() }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    ) {
                        itemsIndexed(
                            items = users,
                            key = { _, user ->
                                user.id
                            }) { index, user ->
                            ProfileCardSmall(githubUser = user, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCardSmall(
    modifier: Modifier = Modifier,
    githubUser: GithubUser,
    navController: NavHostController,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = AppTheme.shape.container,
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            navController.navigate(
                Screens.Profile.createRoute(
                    githubUser.username
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    painter = rememberAsyncImagePainter(model = githubUser.avatarUrl),
                    contentScale = ContentScale.Fit,
                    contentDescription = "User Avatar"
                )
                Text(
                    text = githubUser.username,
                    style = AppTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}