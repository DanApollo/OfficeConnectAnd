package com.sky.officeconnectandroid.navigation

sealed class Screen(val route: String) {
    object AnimatedSplash : Screen("animated_splash")
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")
    object HomeScreen : Screen("home_screen")
}
