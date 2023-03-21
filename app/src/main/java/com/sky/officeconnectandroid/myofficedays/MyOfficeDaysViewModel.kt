package com.sky.officeconnectandroid.myofficedays

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.Appointment
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AppointmentRepository
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository

class MyOfficeDaysViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val appointmentRepository: AppointmentRepository = AppointmentRepository()
) : ViewModel() {
    private val userID: String
        get() = authRepository.getUserId()

    var myOfficeDaysUIState by mutableStateOf(MyOfficeDaysUIState())
        private set

    private fun updateAppointmentsState(input: List<Appointment>) {
        myOfficeDaysUIState = myOfficeDaysUIState.copy(
            appointments = input
        )
    }

    fun updateAppointmentsData() {
        appointmentRepository.setAppointmentEventListener(userID, ::updateAppointmentsState)
    }
}

data class MyOfficeDaysUIState(
    val appointments: List<Appointment> = mutableListOf()
)