package com.sky.officeconnectandroid.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sky.officeconnectandroid.viewmodel.HomeViewModel
import com.sky.officeconnectandroid.models.AppointmentCardModel
import com.sky.officeconnectandroid.view.components.AppointmentCard
import com.sky.officeconnectandroid.view.components.Calendar
import com.sky.officeconnectandroid.view.components.PopupModal
import com.sky.officeconnectandroid.view.components.SkyButton
import java.time.LocalDate

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    onNavToMyProfile: () -> Unit,
) {
    val homeUIState = homeViewModel.homeUIState

    var expanded by remember {
        mutableStateOf(false)
    }
    var opened by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        val date = LocalDate.now()
        homeViewModel.setDate(date)
        homeViewModel.setAppointmentsListState(date)
        homeViewModel.setUserEventListener()

    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(
                text = "Office Connect",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onNavToMyProfile.invoke() }
                    .align(Alignment.CenterEnd)
                    .padding(10.dp)
            )
        }
        Calendar(
            selectedDate = homeUIState.date,
            onDayClick = { day ->
                homeViewModel.setDate(day)
                homeViewModel.setAppointmentsListState(day)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SkyButton(
                onClick = { opened = true },
                text = "Book a Day",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .padding(top = 10.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    homeUIState.appointments.size
                ) { i ->
                    AppointmentCard(
                        isUser = false,
                        appointment = AppointmentCardModel(
                            name = homeUIState.appointments[i].name,
                            location = "",
                            department = homeUIState.appointments[i].department,
                            jobTitle = homeUIState.appointments[i].jobTitle,
                            date = "",
                        ),
                        onDelete = {}
                    )
                }
            }
        }
    }
    if (opened) {
        PopupModal(
            toggleOff = { opened = false },
            title = "Book a Day"
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "book a day for ${homeUIState.date}?"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { opened = false }) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            homeViewModel.createAppointment()
                            opened = false
                        }
                    ) {
                        Text(text = "Book Day")
                    }
                }
            }
        }
    }
}