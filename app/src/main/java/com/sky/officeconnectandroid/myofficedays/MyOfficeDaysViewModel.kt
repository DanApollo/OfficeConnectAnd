package com.sky.officeconnectandroid.myofficedays

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.Appointment
import com.sky.officeconnectandroid.models.AppointmentCard
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AppointmentRepository
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import java.time.LocalDate
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

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy")
    private fun updateAppointmentsState(input: List<Appointment>) {
        myOfficeDaysUIState = myOfficeDaysUIState.copy(
            appointments = input.map { AppointmentCard(name = myOfficeDaysUIState.name, date = it.date.format(formatter), location = it.location) }
        )
    }
    private fun updateUserState(input: User?) {
        myOfficeDaysUIState = myOfficeDaysUIState.copy(
            name = input?.name ?: ""
        )
    }

    fun deleteAppointment(date: String, location: String) {
        appointmentRepository.deleteAppointment(LocalDate.parse(date, formatter), location, "Sky Go", userID)
    }
    fun updateAppointmentsData() {
        appointmentRepository.setAppointmentEventListener(userID, ::updateAppointmentsState)
    }
    fun updateUserData() {
        userRepository.setUserEventListener(userID, ::updateUserState)
    }
}

data class MyOfficeDaysUIState(
    val appointments: List<AppointmentCard> = listOf(),
    val name: String = "",
)