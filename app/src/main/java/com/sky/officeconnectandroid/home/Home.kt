package com.sky.officeconnectandroid.home

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.sky.officeconnectandroid.components.Calendar
import com.sky.officeconnectandroid.components.MyAppointmentCard
import java.time.LocalDate

@Composable
fun Home(
    homeViewModel: HomeViewModel? = null,
    onNavToMyProfile: () -> Unit,
    onNavToNewOfficeDay: () -> Unit
) {
    val homeUIState = homeViewModel?.homeUIState

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(),
    ){
        var opened by remember {
            mutableStateOf<Boolean>(false)
        }

        Column(
            Modifier
                .fillMaxSize()
        ) {
            Button(onClick = { onNavToMyProfile.invoke() }) {
                Text(text = "My Profile")
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
                        bookingDate = homeUIState?.date ?: LocalDate.now(),
                        onDayClick = { day ->
                            homeViewModel?.setDate(day)
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
                    text = homeUIState?.date.toString(),
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
                            homeUIState?.date?.let { homeViewModel?.updateAppointmentsListState(it) }
                        }
                    ) {
                        Text(text = "Osterley")
                    }
                    DropdownMenuItem(
                        onClick = {
                            homeViewModel?.updateLocation("Leeds")
                            homeUIState?.date?.let { homeViewModel?.updateAppointmentsListState(it) }
                        }
                    ) {
                        Text(text = "Leeds")
                    }
                }
                Button(onClick = { opened = true }) {
                    Text(text = "Book a day")
                    Log.d("testLog", "$homeUIState")
                }
            }

            homeViewModel?.updateUserData()

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(homeUIState?.appointments?.size!!) { i ->
                    MyAppointmentCard(name = homeUIState.appointments[i].name, jobTitle = homeUIState.appointments[i].jobTitle, department = homeUIState.appointments[i].department)
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
                        Text(text = "Book a day for ${homeUIState?.date}")
                        Button(onClick = { opened = false }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun HomePreview() {
    Home(onNavToMyProfile = {}, onNavToNewOfficeDay = {})
}