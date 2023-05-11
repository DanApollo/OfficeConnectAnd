package com.sky.officeconnectandroid.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val CALENDAR_ROWS = 6
private const val CALENDAR_COLUMNS = 7
private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM - yyyy")
@Composable
fun Calendar(
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onDayClick(selectedDate.minusMonths(1)) }
            )
            Text(text = selectedDate.format(formatter))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onDayClick(selectedDate.plusMonths(1)) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            Arrangement.SpaceEvenly
        ) {
            for (day in listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")) {
                Column(
                    modifier = Modifier
                        .width(50.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        Alignment.Center
                    ) {
                        Text(text = day)
                    }
                }
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
    Calendar(
        selectedDate = LocalDate.now(),
        onDayClick = {}
    )
}