package com.sky.officeconnectandroid.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sky.officeconnectandroid.viewmodel.MyOfficeDaysViewModel
import com.sky.officeconnectandroid.view.components.AppointmentCard

@Composable
fun MyOfficeDays(
    myOfficeDaysViewModel: MyOfficeDaysViewModel? = null,
    onNavToMyProfile: () -> Unit,
    onNavToHomePage: () -> Unit,
) {
    val myOfficeDaysUIState = myOfficeDaysViewModel?.myOfficeDaysUIState

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onNavToMyProfile.invoke() }
                    .align(Alignment.CenterStart)
                    .padding(10.dp)
            )
            Text(
                text = "My Office Days",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onNavToHomePage.invoke() }
                    .align(Alignment.CenterEnd)
                    .padding(10.dp)
            )
        }
        myOfficeDaysViewModel?.updateAppointmentsData()
        myOfficeDaysViewModel?.updateUserData()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                myOfficeDaysUIState?.appointments?.size!!
            ) { i ->
                AppointmentCard(
                    isUser = true,
                    appointment = myOfficeDaysUIState.appointments[i],
                    onDelete = {
                        myOfficeDaysViewModel
                            .deleteAppointment(
                                myOfficeDaysUIState.appointments[i].date,
                                myOfficeDaysUIState.appointments[i].location
                            )
                    }
                )
            }
        }
    }
}