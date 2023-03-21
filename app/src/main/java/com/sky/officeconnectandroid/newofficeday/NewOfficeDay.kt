package com.sky.officeconnectandroid.newofficeday

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sky.officeconnectandroid.components.DateSelector

@Composable
fun NewOfficeDay(
    newOfficeDayViewModel: NewOfficeDayViewModel? = null,
    onNavToHomePage: () -> Unit
) {
    val newOfficeDayUIState = newOfficeDayViewModel?.newOfficeDayUIState
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        newOfficeDayViewModel?.updateUserData()
        Button(onClick = { onNavToHomePage.invoke() }) {
            Text(text = "Back")
        }
        Text(text = "New Office Day")
        DateSelector(context = context, dateSelect = newOfficeDayViewModel!!::onDateChange)
        Button(onClick = { newOfficeDayViewModel.createAppointment() }) {
            Text(text = "Book a day")
        }
    }
}