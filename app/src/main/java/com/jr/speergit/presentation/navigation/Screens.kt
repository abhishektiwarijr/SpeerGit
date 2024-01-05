package com.jr.speergit.presentation.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object Home : Screens("home_screen")
    object Profile : Screens("{user}/profile_screen")
    object List : Screens("{user}/{title}/list_screen")

    fun createRoute(user:String) = "$user/profile_screen"
    fun createRouteFollowersFollowings(user:String, title: String) = "$user/$title/list_screen"
}