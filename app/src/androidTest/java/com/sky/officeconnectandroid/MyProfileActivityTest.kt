package com.sky.officeconnectandroid.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.sky.officeconnectandroid.MainActivity
import com.sky.officeconnectandroid.viewmodel.MyProfileViewModel
import org.junit.Rule
import org.junit.Test

class MyProfileActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun myProfileScreenDisplayed() {
        composeTestRule.setContent {
            MyProfile(
                myProfileViewModel = MyProfileViewModel(),
                onNavToHomePage = {},
                onNavToMyOfficeDays = {},
                onNavToLoginPage = {}
            )
        }

        // Assert that the "My Profile" text is displayed
        composeTestRule.onNodeWithText("My Profile").assertIsDisplayed()
    }

    @Test
    fun backButtonClicked_navigatesToHomePage() {
        var homePageNavigated = false
        composeTestRule.setContent {
            MyProfile(
                myProfileViewModel = MyProfileViewModel(),
                onNavToHomePage = { homePageNavigated = true },
                onNavToMyOfficeDays = {},
                onNavToLoginPage = {}
            )
        }

        // Click on the back button
        composeTestRule.onNodeWithContentDescription("ArrowBack").performClick()

        // Assert that the navigation to the home page is triggered
        assert(homePageNavigated)
    }

    @Test
    fun logOutButtonClicked_displaysLogOutPopup() {
        composeTestRule.setContent {
            MyProfile(
                myProfileViewModel = MyProfileViewModel(),
                onNavToHomePage = {},
                onNavToMyOfficeDays = {},
                onNavToLoginPage = {}
            )
        }

        // Click on the log out button
        composeTestRule.onNodeWithContentDescription("ExitToApp").performClick()

        // Assert that the log out popup is displayed
        composeTestRule.onNodeWithText("Log out").assertIsDisplayed()
    }

    @Test
    fun deleteProfileTextClicked_displaysDeleteAccountPopup() {
        composeTestRule.setContent {
            MyProfile(
                myProfileViewModel = MyProfileViewModel(),
                onNavToHomePage = {},
                onNavToMyOfficeDays = {},
                onNavToLoginPage = {}
            )
        }

        // Click on the "Delete Profile" text
        composeTestRule.onNodeWithText("Delete Profile").performClick()

        // Assert that the delete account popup is displayed
        composeTestRule.onNodeWithText("Delete Account").assertIsDisplayed()
    }

    // Add more UI tests for other interactions and scenarios as needed

}