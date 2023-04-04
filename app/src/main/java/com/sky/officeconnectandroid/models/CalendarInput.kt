package com.sky.officeconnectandroid.models

data class CalendarInput(
    val day: Int,
    val toDos: List<String> = emptyList()
)
