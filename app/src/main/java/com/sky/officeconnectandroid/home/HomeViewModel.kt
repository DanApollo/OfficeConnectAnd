package com.sky.officeconnectandroid.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AppointmentRepository
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import java.time.LocalDate

class HomeViewModel (
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val appointmentRepository: AppointmentRepository = AppointmentRepository()
) : ViewModel() {
    private val userID: String
        get() = authRepository.getUserId()

    var homeUIState by mutableStateOf(HomeUIState())
        private set

    fun testFun (input: List<String>) {
        val app: MutableList<User> = mutableListOf()
        input.map {
//            Log.d("testLog", "pass: $it")
            userRepository.setUserEventListener(it,
                fun(i: User?) { app.add(i?: User()); homeUIState = homeUIState.copy( appointments = app); Log.d("testLog", "${homeUIState.appointments}");})
        }
    }

    fun updateAppointmentsListState(date: LocalDate) {
        homeUIState = homeUIState.copy(
            appointments = mutableListOf()
        )
        appointmentRepository.getAppointmentOnDayEventListener(date, homeUIState.location, homeUIState.department, ::testFun)
    }

    fun updateLocation(location: String) {
        homeUIState = homeUIState.copy(
            location = location
        )
    }

    private fun updateUserState(input: User?) {
        homeUIState = homeUIState.copy(
            department = input?.department ?: ""
        )
    }

    fun updateUserData() {
        userRepository.setUserEventListener(userID, ::updateUserState)
    }

    fun createAppointment() {
        if (homeUIState.date !== null){
            appointmentRepository.updateAppointment(
                homeUIState.date!!,
                homeUIState.location,
                homeUIState.department,
                userID
            )
        }
    }

    fun setDate(date: LocalDate) {
        homeUIState = homeUIState.copy(
            date = date
        )
    }
}

data class HomeUIState(
    val appointments: MutableList<User> = mutableListOf(),
    val userIDs: List<String> = listOf(),
    val location: String = "Osterley",
    val department: String = "",
    val date: LocalDate = LocalDate.now()
)