package com.sky.officeconnectandroid.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sky.officeconnectandroid.components.DateSelector

@Composable
fun Home(
    onNavToLoginPage: () -> Unit,
    onNavToMyProfile: () -> Unit,
    onNavToNewOfficeDay: () -> Unit
) {
    val context = LocalContext.current
    // Temporary home screen
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Text(text = "This is the Home Screen.")
        Button(onClick = { onNavToMyProfile.invoke() }) {
            Text(text = "My Profile")
        }
        Button(onClick = { onNavToNewOfficeDay.invoke() }) {
            Text(text = "Book a day")
        }
        Button(onClick = {
            Firebase.auth.signOut()
            onNavToLoginPage.invoke()
        }) {
            Text(text = "Log Out")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomePreview() {
    Home(onNavToLoginPage = {}, onNavToMyProfile = {}, onNavToNewOfficeDay = {})
}