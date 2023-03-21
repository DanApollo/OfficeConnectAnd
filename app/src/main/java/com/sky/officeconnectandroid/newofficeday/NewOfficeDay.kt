package com.sky.officeconnectandroid.newofficeday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewOfficeDay(
    newOfficeDayViewModel: NewOfficeDayViewModel? = null,
    onNavToHomePage: () -> Unit
) {
    val newOfficeDayUIState = newOfficeDayViewModel?.newOfficeDayUIState
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        newOfficeDayViewModel?.updateUserData()
        Button(onClick = { onNavToHomePage.invoke() }) {
            Text(text = "Back")
        }
        Text(text = "New Office Day")
        TextField(value = newOfficeDayUIState?.date ?: "", onValueChange = { newOfficeDayViewModel?.onDateChange(it) })
        Button(onClick = { newOfficeDayViewModel?.createAppointment() }) {
            Text(text = "Make a day")
        }
    }

}