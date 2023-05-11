package com.sky.officeconnectandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AppointmentRepository
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import java.time.LocalDate

class HomeViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val appointmentRepository: AppointmentRepository = AppointmentRepository()
) : ViewModel() {
    private val userID: String
        get() = authRepository.getUserId()

    var homeUIState by mutableStateOf(HomeUIState())
        private set

    fun setUserEventListener() {
        userRepository.setUserEventListener(userID, ::updateUserState)
    }
    fun setDate(date: LocalDate) {
        homeUIState = homeUIState.copy(
            date = date
        )
    }

    fun setLocation(location: String) {
        homeUIState = homeUIState.copy(
            location = location
        )
    }


    private fun getAppointmentUsers(input: List<String>) {
        userRepository.getUsers { userMap ->
            val profiles = userMap.filter { input.contains(it.key) }.map { it.value }.toList()
            homeUIState = homeUIState.copy(appointments = profiles)
        }
    }

    fun setAppointmentsListState(date: LocalDate) {
        appointmentRepository.getAppointmentOnDayEventListener(
            date,
            homeUIState.location,
            homeUIState.department,
            ::getAppointmentUsers
        )
    }



    private fun updateUserState(input: User?) {
        homeUIState = homeUIState.copy(
            department = input?.department ?: "",
            location = input?.location ?: "",
        )
    }

    fun updateUserData() {

    }

    fun createAppointment() {
        appointmentRepository.updateAppointment(
            homeUIState.date,
            homeUIState.location,
            homeUIState.department,
            userID
        )
    }
}

data class HomeUIState(
    val appointments: List<User> = listOf(),
    val userIDs: List<String> = listOf(),
    val location: String = "",
    val department: String = "",
    val date: LocalDate = LocalDate.now()
)