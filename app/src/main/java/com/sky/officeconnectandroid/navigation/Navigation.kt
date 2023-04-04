package com.sky.officeconnectandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky.officeconnectandroid.animatedsplash.AnimatedSplash
import com.sky.officeconnectandroid.home.Home
import com.sky.officeconnectandroid.home.HomeViewModel
import com.sky.officeconnectandroid.login.LoginScreen
import com.sky.officeconnectandroid.login.LoginViewModel
import com.sky.officeconnectandroid.login.SignUpScreen
import com.sky.officeconnectandroid.myofficedays.MyOfficeDays
import com.sky.officeconnectandroid.myofficedays.MyOfficeDaysViewModel
import com.sky.officeconnectandroid.myprofile.MyProfile
import com.sky.officeconnectandroid.myprofile.MyProfileViewModel
import com.sky.officeconnectandroid.newofficeday.NewOfficeDay
import com.sky.officeconnectandroid.newofficeday.NewOfficeDayViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    myProfileViewModel: MyProfileViewModel,
    newOfficeDayViewModel: NewOfficeDayViewModel,
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
                onNavToNewOfficeDay = {
                    navController.navigate(Screen.NewOfficeDay.route) {
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
        composable(route = Screen.NewOfficeDay.route) {
            NewOfficeDay(
                onNavToHomePage = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                    }
                },
                newOfficeDayViewModel = newOfficeDayViewModel
            )
        }
        composable(route = Screen.MyOfficeDays.route) {
            MyOfficeDays(
                onNavToMyProfile = {
                    navController.navigate(Screen.MyProfile.route) {
                        launchSingleTop = true
                    }
                },
                myOfficeDaysViewModel = myOfficeDaysViewModel
            )
        }
    }
}
