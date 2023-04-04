package com.sky.officeconnectandroid.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky.officeconnectandroid.components.AppointmentCard
import com.sky.officeconnectandroid.components.Calendar
import java.time.LocalDate
import kotlin.math.exp

@Composable
fun Home(
    homeViewModel: HomeViewModel? = null,
    onNavToMyProfile: () -> Unit,
    onNavToNewOfficeDay: () -> Unit
) {
    var homeUIState = homeViewModel?.homeUIState

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(),
    ){
        var opened by remember {
            mutableStateOf<Boolean>(true)
        }
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Button(onClick = { onNavToMyProfile.invoke() }) {
                Text(text = "My Profile")
            }

            var clickedCalendarElem by remember {
                mutableStateOf<LocalDate>(LocalDate.now())
            }
            var expanded by remember {
                mutableStateOf(false)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
                contentAlignment = Alignment.TopCenter
            ) {
                Calendar(
                    onDayClick = { day ->
                        clickedCalendarElem = day
                        homeViewModel?.updateAppointmentsListState(day)
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.3f)
                )
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ) {
                Text(
                    text = clickedCalendarElem.toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
                Button(onClick = { expanded = !expanded }) {
                    homeUIState?.location?.let { Text(text = it) }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            homeViewModel?.updateLocation("Osterley")
                            homeViewModel?.updateAppointmentsListState(clickedCalendarElem)
                        }
                    ) {
                        Text(text = "Osterley")
                    }
                    DropdownMenuItem(
                        onClick = {
                            homeViewModel?.updateLocation("Leeds")
                            homeViewModel?.updateAppointmentsListState(clickedCalendarElem)
                        }
                    ) {
                        Text(text = "Leeds")
                    }
                }
                Button(onClick = { onNavToNewOfficeDay.invoke() }) {
                    Text(text = "Book a day")
                }
            }

            homeViewModel?.updateUserData()

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(homeUIState?.appointments?.size!!) { i ->
                    AppointmentCard(name = homeUIState.appointments[i].name, date = "", location = "")
                }
            }
        }
        if (opened) {
            Box(
                modifier = Modifier.size(width = 50.dp, height = 50.dp),
            )
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun HomePreview() {
    Home(onNavToMyProfile = {}, onNavToNewOfficeDay = {})
}