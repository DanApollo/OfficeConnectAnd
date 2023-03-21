package com.sky.officeconnectandroid.models

import java.time.LocalDate

data class Appointment(
    val date: LocalDate,
    val location: String = "",
)
