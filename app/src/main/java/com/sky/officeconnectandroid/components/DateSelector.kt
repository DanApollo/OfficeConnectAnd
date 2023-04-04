package com.sky.officeconnectandroid.components

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.util.Date

@Composable
fun DateSelector(context: Context, dateSelect: (date: LocalDate) -> Unit) {
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        {
            _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$year-${if (month < 9) "0${month+1}" else "${month+1}"}-${if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"}"
            val newDate = LocalDate.parse(date.value)
            dateSelect(newDate)
        }, year, month, day
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Selected date: ${date.value}")
        Button(onClick = { datePickerDialog.show() }) {
            Text(text = "Open date picker")
        }
    }
}