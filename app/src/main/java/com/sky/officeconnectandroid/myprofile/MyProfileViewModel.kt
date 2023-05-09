package com.sky.officeconnectandroid.myprofile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {
    private val TAG: String = "MyProfileViewModel"
    private val userID: String
        get() = authRepository.getUserId()

    var myProfileUIState by mutableStateOf(MyProfileUIState())
        private set

    private fun updateUserState(input: User?) {
        myProfileUIState = myProfileUIState.copy(
            user = input?: User(),
            editableName = input?.name ?: "",
            editableJobTitle = input?.jobTitle ?: "",
            editableLocation = input?.location ?: "",
            editableDepartment = input?.department ?: "",
        )
    }
    init {
        userRepository.setUserEventListener(userID, ::updateUserState)
    }
    fun onNameChange(name: String) {
        myProfileUIState = myProfileUIState.copy(editableName = name)
    }

    fun onJobTitleChange(jobTitle: String) {
        myProfileUIState = myProfileUIState.copy(editableJobTitle = jobTitle)
    }

    fun onLocationChange(location: String) {
        myProfileUIState = myProfileUIState.copy(editableLocation = location)
    }

    fun onDepartmentChange(department: String) {
        myProfileUIState = myProfileUIState.copy(editableDepartment = department)
    }

    fun onPasswordChange(password: String) {
        myProfileUIState = myProfileUIState.copy(password = password)
    }

    fun onCancelEdit() {
        myProfileUIState = myProfileUIState.copy(
            editableName = myProfileUIState.user.name,
            editableJobTitle = myProfileUIState.user.jobTitle,
            editableLocation = myProfileUIState.user.location,
            editableDepartment = myProfileUIState.user.department,
        )
    }

    fun onUpdateUser() {
        val user = User(
            myProfileUIState.editableName,
            myProfileUIState.editableLocation,
            myProfileUIState.editableDepartment,
            myProfileUIState.editableJobTitle,
            myProfileUIState.user.appointments
        )
        userRepository.updateUser(userID, user)
    }
    fun onDeleteUser(password: String) = viewModelScope.launch {
        authRepository.reAuthUser(password) { isSuccessful ->
            if (isSuccessful) {
                Log.d(TAG, "Successful Re-Auth")
            } else {
                Log.e(TAG, "ERROR")
            }
        }
    }
}

data class MyProfileUIState(
    val user: User = User(),
    val editableName: String = "",
    val editableJobTitle: String = "",
    val editableLocation: String = "",
    val editableDepartment: String = "",
    val password: String = "weewoo"
)