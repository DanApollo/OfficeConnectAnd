package com.sky.officeconnectandroid.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
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

    var bookDayPopup by remember {
        mutableStateOf(false)
    }
    var selectLocation by remember {
        mutableStateOf(false)
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
            heatMap = homeUIState.heatMap,
            today = homeUIState.today,
            onDayClick = { day ->
                homeViewModel.filterUsersOnDay(homeUIState.monthAppointments,day, "Osterley")
            },
            onMonthClick = {day ->
                homeViewModel.filterUsersOnMonth(homeUIState.users, day, "Osterley")
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ){
                if (homeUIState.userDay) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .padding(top = 10.dp)
                            .align(Alignment.Center),
                        Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "See you there!")
                            Text(
                                text = "You've booked this day.",
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                        }

                    }
                } else {
                    SkyButton(
                        onClick = { bookDayPopup = true },
                        text = "Book a Day",
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .padding(top = 10.dp)
                            .align(Alignment.Center)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .height(50.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            selectLocation = true
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
//                    if (selectLocation) {
//                        Popup(
//                            onDismissRequest = { selectLocation = false }
//                        ) {
//                            LazyColumn(){
//                                items(
//                                    2
//                                ) {
//                                    Text(
//                                        text = "Osterley",
//                                        modifier = Modifier.clickable {
//                                            homeViewModel.setLocation("Osterley")
//                                            selectLocation = false
//                                        })
//                                    Text(
//                                        text = "Leeds",
//                                        modifier = Modifier.clickable {
//                                            homeViewModel.setLocation("Leeds")
//                                            selectLocation = false
//                                        })
//                                }
//                            }
//                        }
//                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    homeUIState.dayAppointments.size
                ) { i ->
                    AppointmentCard(
                        isUser = false,
                        appointment = AppointmentCardModel(
                            name = homeUIState.dayAppointments[i].name,
                            location = "",
                            department = homeUIState.dayAppointments[i].department,
                            jobTitle = homeUIState.dayAppointments[i].jobTitle,
                            date = "",
                        ),
                        onDelete = {}
                    )
                }
            }
        }
    }
    if (bookDayPopup) {
        PopupModal(
            toggleOff = { bookDayPopup = false },
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
                    Button(onClick = { bookDayPopup = false }) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            homeViewModel.createAppointment()
                            bookDayPopup = false
                        }
                    ) {
                        Text(text = "Book Day")
                    }
                }
            }
        }
    }
}