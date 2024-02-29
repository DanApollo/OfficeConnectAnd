package com.sky.officeconnectandroid.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    heatMap: List<List<Int>>,
    today: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    onMonthClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onMonthClick(selectedDate.minusMonths(1)) }
            )
            Text(text = selectedDate.format(formatter))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onMonthClick(selectedDate.plusMonths(1)) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            Arrangement.SpaceEvenly
        ) {
            for (day in listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")) {
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
                    .height(55.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (j in 0 until CALENDAR_COLUMNS) {
                    val boxNumber = j + (i * 7) - (selectedDate.withDayOfMonth(1).dayOfWeek.value - 2)
                    Column(
                        modifier = Modifier
                            .width(55.dp),
                    ) {
                        if (boxNumber in 1..selectedDate.lengthOfMonth()) {
                            val population = (if (heatMap[boxNumber][0] < 10) heatMap[boxNumber][0] else 10).toFloat()
                            OutlinedButton(
                                onClick = { onDayClick(selectedDate.withDayOfMonth(boxNumber)) },
                                border = BorderStroke(
                                    2.dp,
                                    if (selectedDate.dayOfMonth == boxNumber) Color.Blue
                                            else Color.LightGray
                                ),
                                shape = CircleShape,
                                colors = ButtonDefaults
                                    .buttonColors(
                                        backgroundColor =
                                        if (selectedDate.withDayOfMonth(boxNumber) == today) Color.Blue.copy(alpha = 0.3f) else
                                        Color(255, 0, 0).copy(alpha = population / 10)),
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Box(Modifier.fillMaxSize()) {
                                    if (heatMap[boxNumber][1] != 0) {
                                        Box(
                                            modifier = Modifier
                                                .size(5.dp).clip(CircleShape)
                                                .background(color = Color.Green)
                                                .align(Alignment.TopCenter)
                                        )
                                    }
                                    Text(
                                        text = "$boxNumber",
                                        color = if (selectedDate.dayOfMonth == boxNumber) Color.Blue else Color.Black,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PrevNewCalendar(
) {
    Calendar(
        selectedDate = LocalDate.now(),
        heatMap = listOf(),
        today = LocalDate.now(),
        onDayClick = {},
        onMonthClick = {}
    )
}