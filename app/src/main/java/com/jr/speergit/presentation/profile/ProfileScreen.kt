package com.jr.speergit.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jr.speergit.domain.model.GithubUser
import com.jr.speergit.presentation.navigation.Screens
import com.jr.speergit.presentation.ui.theme.AppTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    githubUser: GithubUser,
) {
    val onFollowersClick = remember {
        {
            navController.navigate(
                Screens.List.createRouteFollowersFollowings(
                    githubUser.username,
                    "followers"
                )
            )
        }
    }

    val onFollowingsClick = remember {
        {
            navController.navigate(
                Screens.List.createRouteFollowersFollowings(
                    githubUser.username,
                    "followings"
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(
            name = githubUser.username,
            modifier = Modifier
        ) { navController.navigateUp() }
        Spacer(modifier = Modifier.height(20.dp))
        ProfileSection(
            user = githubUser,
            onFollowersClick = onFollowersClick,
            onFollowingsClick = onFollowingsClick
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun TopBar(
    name: String,
    modifier: Modifier = Modifier,
    goBack: () -> Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable { goBack() },
            tint = AppTheme.colorScheme.iconTint
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = name,
            color = AppTheme.colorScheme.textColor,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    user: GithubUser,
    onFollowersClick: () -> Unit,
    onFollowingsClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RoundImage(
                image = rememberAsyncImagePainter(user.avatarUrl),
                modifier = Modifier
                    .size(90.dp)
                    .weight(2.5f),
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(
                modifier = Modifier.weight(7.5f),
                user = user,
                onFollowersClick = onFollowersClick,
                onFollowingsClick = onFollowingsClick
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val letterSpacing = 0.5.sp
            val lineHeight = 20.sp
            Text(
                text = user.name,
                color = AppTheme.colorScheme.textColor,
                fontWeight = FontWeight.Bold,
                letterSpacing = letterSpacing,
                lineHeight = lineHeight
            )
            Text(
                text = user.description,
                color = AppTheme.colorScheme.textColor,
                letterSpacing = letterSpacing,
                lineHeight = lineHeight
            )
        }
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun StatSection(
    modifier: Modifier = Modifier,
    user: GithubUser,
    onFollowersClick: () -> Unit,
    onFollowingsClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(
            numberText = "${user.repositories}",
            text = "Repositories",
            onClick = {}
        )
        ProfileStat(
            numberText = "${user.followers}",
            text = "Followers",
            onClick = onFollowersClick
        )
        ProfileStat(
            numberText = "${user.following}",
            text = "Following",
            onClick = onFollowingsClick
        )
    }
}

@Composable
fun ProfileStat(
    modifier: Modifier = Modifier,
    numberText: String,
    text: String,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        Text(
            text = numberText,
            color = AppTheme.colorScheme.textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = AppTheme.colorScheme.textColor
        )
    }
}