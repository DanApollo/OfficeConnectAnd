package com.sky.officeconnectandroid.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.sky.officeconnectandroid.components.MyAppointmentCard
import com.sky.officeconnectandroid.components.NewCalendar
import com.sky.officeconnectandroid.components.SkyColourText

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
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ){
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.End
            ) {
                Button(onClick = { onNavToMyProfile.invoke() }) {
                    Text(text = "My Profile")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
                contentAlignment = Alignment.TopCenter
            ) {
                NewCalendar(selectedDate = homeUIState.date, onDayClick = { day ->
                    homeViewModel.setDate(day)
                    homeViewModel.setAppointmentsListState(day)
                })
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ) {
                Text(
                    text = homeUIState.date.toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
                Button(onClick = { expanded = !expanded }) {
                    Text(text = homeUIState.location)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            homeViewModel.setLocation("Osterley")
                            homeViewModel.setAppointmentsListState(homeUIState.date)
                        }
                    ) {
                        Text(text = "Osterley")
                    }
                    DropdownMenuItem(
                        onClick = {
                            homeViewModel.setLocation("Leeds")
                            homeViewModel.setAppointmentsListState(homeUIState.date)
                        }
                    ) {
                        Text(text = "Leeds")
                    }
                }
                Button(onClick = { opened = true }) {
                    Text(text = "Book a day")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(
                    homeUIState.appointments.size
                ) { i ->
                    MyAppointmentCard(
                        name = homeUIState.appointments[i].name,
                        jobTitle = homeUIState.appointments[i].jobTitle,
                        department = homeUIState.appointments[i].department
                    )
                }
            }
        }
        if (opened) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { opened = false}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(100, 100, 100).copy(0.8f)),

                    ){
                    Column(modifier = Modifier
                        .size(width = 300.dp, height = 400.dp)
                        .align(Alignment.Center)
                        .background(color = Color.White)
                    ) {
                        SkyColourText(
                            text = "Book a Day",
                            modifier = Modifier
                        )
                        Text(text = homeUIState.date.toString())
                        Button(onClick = { homeViewModel.createAppointment() }) {
                            Text(text = "Book Day")
                        }
                        Button(onClick = { opened = false }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}