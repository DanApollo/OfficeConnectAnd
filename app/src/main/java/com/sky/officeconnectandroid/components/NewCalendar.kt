package com.sky.officeconnectandroid.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate

private const val CALENDAR_ROWS = 6
private const val CALENDAR_COLUMNS = 7

@Composable
fun NewCalendar(
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = { onDayClick(selectedDate.minusMonths(1)) }) {
                Text(text = "<")
            }
            Text(text = selectedDate.month.toString())
            Button(onClick = { onDayClick(selectedDate.plusMonths(1)) }) {
                Text(text = ">")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            Arrangement.SpaceEvenly
        ) {
            for (day in listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")) {
                Text(text = day)
            }

        }
        for (i in 0 until CALENDAR_ROWS) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                Arrangement.SpaceEvenly
            ) {
                for (j in 0 until CALENDAR_COLUMNS) {
                    val boxNumber = j+(i*7)+1 - (selectedDate.withDayOfMonth(1).dayOfWeek.value - 1)
                    Column(
                        modifier = Modifier
                            .width(50.dp),
                    ) {
                        if (boxNumber > 0 && boxNumber <= selectedDate.lengthOfMonth()){
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onDayClick(selectedDate.withDayOfMonth(boxNumber)) }
                                    .background(color = if (selectedDate.dayOfMonth == boxNumber) Color.Red else Color.White),
                            ) {
                                Text(text = "$boxNumber")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun PrevNewCalendar(
) {
    NewCalendar(
        selectedDate = LocalDate.now(),
        onDayClick = {}
    )
}