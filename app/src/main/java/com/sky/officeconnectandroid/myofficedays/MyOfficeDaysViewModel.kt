package com.sky.officeconnectandroid.myofficedays

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.Appointment
import com.sky.officeconnectandroid.models.DisplayAppointment
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AppointmentRepository
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import java.time.format.DateTimeFormatter

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
        val formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy")
        myOfficeDaysUIState = myOfficeDaysUIState.copy(
            appointments = input.map { DisplayAppointment(date = it.date.format(formatter), location = it.location) }
        )
    }
    private fun updateUserState(input: User?) {
        Log.d("testObj", input?.appointments.toString())
        myOfficeDaysUIState = myOfficeDaysUIState.copy(
            name = input?.name ?: ""
        )
    }

    fun updateAppointmentsData() {
        appointmentRepository.setAppointmentEventListener(userID, ::updateAppointmentsState)
    }
    fun updateUserData() {
        userRepository.setUserEventListener(userID, ::updateUserState)
    }
}

data class MyOfficeDaysUIState(
    val appointments: List<DisplayAppointment> = mutableListOf(),
    val name: String = "",
)