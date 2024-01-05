package com.jr.speergit.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jr.speergit.R
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.presentation.components.SearchBar
import com.jr.speergit.presentation.navigation.Screens
import com.jr.speergit.presentation.ui.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val searchText by homeViewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by homeViewModel.isSearching.collectAsStateWithLifecycle()
    val uiState by homeViewModel.githubUser.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
        ,
        verticalArrangement = Arrangement.Top
    ) {
        val notFoundText = remember {
            mutableStateOf("Looking for some great collaborators out there?")
        }
        TitleHeader()
        Spacer(modifier = Modifier.height(10.dp))
        SearchBar(searchText, homeViewModel)
        if (isSearching) {
            LoadingView()
        } else {
            when (uiState) {
                is HomeScreenUiState.Empty -> {
                    NotFoundView(text = notFoundText.value)
                }

                is HomeScreenUiState.Loading -> {
                    LoadingView()
                }

                is HomeScreenUiState.Error -> {
                    ErrorMessageView(error = (uiState as HomeScreenUiState.Error).error)
                }

                is HomeScreenUiState.Success -> {
                    ProfileCard(
                        githubUser = (uiState as HomeScreenUiState.Success).data,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TitleHeader(modifier: Modifier = Modifier) {
    val textColor = AppTheme.colorScheme.iconTint
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = ParagraphStyle(lineHeight = 30.sp)
            ) {
                withStyle(
                    style = SpanStyle(
                        color = textColor,
                        fontSize = 28.sp
                    )
                ) {
                    append("Look for")
                    appendLine()
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor,
                        fontSize = 28.sp
                    )
                ) {
                    append("Users on GitHub")
                }
            }
        },
    )
}

@Composable
fun ErrorMessageView(modifier: Modifier = Modifier, error: String? = null) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val errorText = remember {
            mutableStateOf(
                if (error.isNullOrBlank()) {
                    "Seems like something unexpected occurred"
                } else {
                    error
                }
            )
        }
        Image(
            modifier = Modifier
                .size(200.dp)
                .width(150.dp),
            painter = painterResource(R.drawable.ic_not_found),
            contentScale = ContentScale.Fit,
            contentDescription = "Not Found"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            textAlign = TextAlign.Center,
            text = errorText.value,
            color = AppTheme.colorScheme.iconTint,
            style = AppTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun NotFoundView(modifier: Modifier = Modifier, text: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .width(150.dp),
            painter = painterResource(R.drawable.ic_looking_for),
            contentScale = ContentScale.Fit,
            contentDescription = "Looking for"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            textAlign = TextAlign.Center,
            text = text,
            color = AppTheme.colorScheme.onBackground,
            style = AppTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCard(
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
                .padding(15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .clip(CircleShape),
                    painter = rememberAsyncImagePainter(model = githubUser.avatarUrl),
                    contentScale = ContentScale.Fit,
                    contentDescription = "User Avatar"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = githubUser.name,
                            style = AppTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                        Text(
                            text = githubUser.username,
                            style = AppTheme.typography.body,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = githubUser.description,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                modifier = Modifier.padding(5.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Person, contentDescription = "Icon Person")
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "${githubUser.followers} followers",
                    maxLines = 1,
                    fontWeight = FontWeight.W500,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = ". ${githubUser.following} following",
                    maxLines = 1,
                    fontWeight = FontWeight.W500,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                )
            }
        }
    }
}