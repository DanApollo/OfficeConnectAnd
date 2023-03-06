package com.sky.officeconnectandroid.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Home(
    onNavToLoginPage: () -> Unit
) {
    // Temporary home screen
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Text(text = "This is the Home Screen.")
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
    Home(onNavToLoginPage = {})
}