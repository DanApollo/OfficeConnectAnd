package com.sky.officeconnectandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky.officeconnectandroid.animatedsplash.AnimatedSplash
import com.sky.officeconnectandroid.home.Home
import com.sky.officeconnectandroid.login.LoginScreen
import com.sky.officeconnectandroid.login.LoginViewModel
import com.sky.officeconnectandroid.login.SignUpScreen
import com.sky.officeconnectandroid.myprofile.MyProfile

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AnimatedSplash.route
    ) {
        // AnimatedSplash will automatically navigate to LoginScreen in 4000 milliseconds.
        composable(route = Screen.AnimatedSplash.route) {
            AnimatedSplash(
                onNavToLoginPage = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.AnimatedSplash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                onNavToHomePage = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                        popUpTo(route = Screen.LoginScreen.route) { inclusive = true }
                    }
                },
                onNavToSignUpPage = {
                    navController.navigate(Screen.SignUpScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                },
                loginViewModel = loginViewModel
            )
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(
                onNavToHomePage = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.SignUpScreen.route) { inclusive = true }
                    }
                },
                onNavToLoginPage = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.SignUpScreen.route) { inclusive = true }
                    }
                },
                loginViewModel = loginViewModel
            )
        }
        composable(route = Screen.HomeScreen.route) {
            Home(
                onNavToLoginPage = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                        popUpTo(route = Screen.HomeScreen.route) { inclusive = true }
                    }
                },
                onNavToMyProfile = {
                    navController.navigate((Screen.MyProfile.route)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Screen.MyProfile.route) {
            MyProfile(
                onNavToHomePage = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
