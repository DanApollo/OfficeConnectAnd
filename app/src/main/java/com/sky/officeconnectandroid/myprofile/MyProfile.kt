package com.sky.officeconnectandroid.myprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyProfile(
    myProfileViewModel: MyProfileViewModel? = null,
    onNavToHomePage: () -> Unit
) {
    val myProfileUIState = myProfileViewModel?.myProfileUIState
    myProfileViewModel?.updateUserData()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = { onNavToHomePage.invoke() }) {
            Text(text = "Back")
        }
        Text(text = "Name:")
        Text(text = myProfileUIState?.name ?: "")
        Text(text = "Job title:")
        Text(text = myProfileUIState?.jobTitle ?: "")
        Text(text = "Location:")
        Text(text = myProfileUIState?.location ?: "")
        Text(text = "Department")
        Text(text = myProfileUIState?.department ?: "")
    }
}

