package com.sky.officeconnectandroid.models

import java.time.LocalDate

data class DisplayAppointment(
    val date: LocalDate = LocalDate.now(),
    val location: String = "",
)
