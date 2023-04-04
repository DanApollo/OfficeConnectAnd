package com.sky.officeconnectandroid.newofficeday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AppointmentRepository
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import java.time.LocalDate

class NewOfficeDayViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val appointmentRepository: AppointmentRepository = AppointmentRepository()
) : ViewModel() {
    private val userID: String
        get() = authRepository.getUserId()

    private fun updateUserState(input: User?) {
        newOfficeDayUIState = newOfficeDayUIState.copy(
            location = input?.location ?: "",
            department = input?.department ?: ""
        )
    }

    fun updateUserData() {
        userRepository.setUserEventListener(userID, ::updateUserState)
    }

    var newOfficeDayUIState by mutableStateOf(NewOfficeDayUIState())
        private set

    fun onDateChange(date: LocalDate) {
        newOfficeDayUIState = newOfficeDayUIState.copy(date = date)
    }

    fun createAppointment() {
        if (newOfficeDayUIState.date !== null){
            appointmentRepository.updateAppointment(
                newOfficeDayUIState.date!!,
                newOfficeDayUIState.location,
                newOfficeDayUIState.department,
                userID
            )
        }
    }
}
data class NewOfficeDayUIState(
    val date: LocalDate? = null,
    val location: String = "",
    val department: String = "",
)