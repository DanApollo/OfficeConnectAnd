package com.sky.officeconnectandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky.officeconnectandroid.view.AnimatedSplash
import com.sky.officeconnectandroid.view.Home
import com.sky.officeconnectandroid.viewmodel.HomeViewModel
import com.sky.officeconnectandroid.view.LoginScreen
import com.sky.officeconnectandroid.viewmodel.LoginViewModel
import com.sky.officeconnectandroid.view.SignUpScreen
import com.sky.officeconnectandroid.view.MyOfficeDays
import com.sky.officeconnectandroid.viewmodel.MyOfficeDaysViewModel
import com.sky.officeconnectandroid.view.MyProfile
import com.sky.officeconnectandroid.viewmodel.MyProfileViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    myProfileViewModel: MyProfileViewModel,
    myOfficeDaysViewModel: MyOfficeDaysViewModel,
    homeViewModel: HomeViewModel
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
                onNavToMyProfile = {
                    navController.navigate(Screen.MyProfile.route) {
                        launchSingleTop = true
                    }
                },
                homeViewModel = homeViewModel
            )
        }
        composable(route = Screen.MyProfile.route) {
            MyProfile(
                onNavToLoginPage = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                        popUpTo(route = Screen.HomeScreen.route) { inclusive = true }
                    }
                },
                onNavToHomePage = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                    }
                },
                onNavToMyOfficeDays = {
                    navController.navigate(Screen.MyOfficeDays.route) {
                        launchSingleTop = true
                    }
                },
                myProfileViewModel = myProfileViewModel
            )
        }
        composable(route = Screen.MyOfficeDays.route) {
            MyOfficeDays(
                onNavToMyProfile = {
                    navController.navigate(Screen.MyProfile.route) {
                        launchSingleTop = true
                    }
                },
                onNavToHomePage = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                    }
                },
                myOfficeDaysViewModel = myOfficeDaysViewModel
            )
        }
    }
}
