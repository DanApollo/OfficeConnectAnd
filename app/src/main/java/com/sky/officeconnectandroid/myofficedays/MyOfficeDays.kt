package com.sky.officeconnectandroid.myofficedays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sky.officeconnectandroid.components.AppointmentCard

@Composable
fun MyOfficeDays(
    myOfficeDaysViewModel: MyOfficeDaysViewModel? = null,
    onNavToMyProfile: () -> Unit
) {
    val myOfficeDaysUIState = myOfficeDaysViewModel?.myOfficeDaysUIState

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        myOfficeDaysViewModel?.updateAppointmentsData()
        myOfficeDaysViewModel?.updateUserData()
        Button(onClick = { onNavToMyProfile.invoke() }) {
            Text(text = "Back")
        }
        Text(text = "My Office Days")
        if (myOfficeDaysUIState?.appointments?.size!! > 0) {
            for (i in myOfficeDaysUIState?.appointments!!) {
                AppointmentCard(
                    name = myOfficeDaysUIState.name,
                    date = i.date,
                    location = i.location)
            }
        }

    }
}