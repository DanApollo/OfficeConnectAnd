package com.sky.officeconnectandroid.viewmodel

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
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val appointmentRepository: AppointmentRepository = AppointmentRepository()
) : ViewModel() {
    private val userID: String
        get() = authRepository.getUserId()

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

    var homeUIState by mutableStateOf(HomeUIState())
        private set

    init {
        setUserEventListener()
        setAppointmentsListState()
    }

    private fun setUserEventListener() {
        userRepository.setUserEventListener(userID) { user ->
            homeUIState = homeUIState.copy(
                department = user?.department ?: "",
                location = user?.location ?: "",
            )
        }
    }

    private fun setAppointmentsListState() {
        userRepository.getUsers { userMap ->
            homeUIState = homeUIState.copy(userDay = false)
            filterUsersOnMonth(userMap, homeUIState.date, homeUIState.location)
            homeUIState = homeUIState
                .copy(
                    users = userMap
                )
        }
    }

    fun filterUsersOnMonth(
        users: Map<String, User>,
        date: LocalDate,
        location: String
    ) {
        homeUIState = homeUIState.copy(
            date = date,
            userDay = false
        )
        val dayAppointments: MutableList<User> = mutableListOf()
        val monthAppointments: MutableMap<String, User> = mutableMapOf()
        val heatMap = MutableList(32) { mutableListOf(0, 0) }
        users.map { user ->
            user.value.appointments.map { appointment ->
                val appDate: LocalDate = LocalDate.parse(appointment.key, formatter)
                if (
                    (appDate.month == date.month) && (appDate.year == date.year)
                ) {
                    val day: Int = appDate.dayOfMonth
                    heatMap[day][0] = heatMap[day][0] + 1
                    monthAppointments[user.key] = user.value
                    if (
                        user.key == userID
                    ) {
                        heatMap[day][1] = 1
                    }
                    if (
                        user.key == userID
                        && appointment.key == date.format(formatter)
                        && appointment.value == location
                    ) {
                        homeUIState = homeUIState.copy(userDay = true)
                    } else if (
                        appointment.key == date.format(formatter)
                        && appointment.value == location
                        && user.key != userID
                    ) {
                        dayAppointments.add(user.value)
                    }
                }
            }
            homeUIState = homeUIState
                .copy(
                    dayAppointments = dayAppointments,
                    monthAppointments = monthAppointments,
                    heatMap = heatMap
                )
        }
    }

    fun filterUsersOnDay(
        users: Map<String, User>,
        date: LocalDate,
        location: String
    ) {
        homeUIState = homeUIState.copy(
            date = date,
            userDay = false
        )
        val dayAppointments: MutableList<User> = mutableListOf()
        users.map { user ->
            user.value.appointments.map { appointment ->
                if (
                    appointment.key == date.format(formatter)
                    && appointment.value == location
                    && user.key == userID
                ) {
                    homeUIState = homeUIState.copy(userDay = true)
                } else if (
                    appointment.key == date.format(formatter)
                    && appointment.value == location
                    && user.key != userID
                ) {
                    dayAppointments.add(user.value)
                }
            }
        }
        homeUIState = homeUIState.copy(dayAppointments = dayAppointments)
    }

    fun setLocation(location: String) {
        homeUIState = homeUIState.copy(location = location)
    }

    fun setDate(date: LocalDate) {
        homeUIState = homeUIState.copy(date = date)
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
    val userDay: Boolean = false,
    val users: Map<String, User> = mapOf(),
    val dayAppointments: List<User> = listOf(),
    val monthAppointments: Map<String, User> = mapOf(),
    val appointments: List<User> = listOf(),
    val heatMap: List<List<Int>> = List(32) { listOf(0,0) },
    val userIDs: List<String> = listOf(),
    val days: Map<Int, Int> = mapOf(),
    val location: String = "",
    val department: String = "",
    val date: LocalDate = LocalDate.now(),
    val today: LocalDate = LocalDate.now()
)