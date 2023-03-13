package com.sky.officeconnectandroid.myprofile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository

class MyProfileViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {
    private val userID: String
        get() = authRepository.getUserId()

    var myProfileUIState by mutableStateOf(MyProfileUIState())
        private set

    private fun updateUserState(input: User?) {
        myProfileUIState = myProfileUIState.copy(
            name = input?.name ?: "",
            jobTitle = input?.jobTitle ?: "",
            location = input?.location ?: "",
            department = input?.department ?: ""
        )
    }
    fun updateUserData() {
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

    fun onEditableToggle() {
        if (!myProfileUIState.editable) {
            myProfileUIState = myProfileUIState.copy(
                editableName = myProfileUIState.name,
                editableJobTitle = myProfileUIState.jobTitle,
                editableLocation = myProfileUIState.location,
                editableDepartment = myProfileUIState.department,
            )
        }
        myProfileUIState = myProfileUIState.copy(editable = !myProfileUIState.editable)
    }

    fun onUpdateUser() {
        val user = User(
            myProfileUIState.editableName,
            myProfileUIState.editableLocation,
            myProfileUIState.editableDepartment,
            myProfileUIState.editableJobTitle
        )
        userRepository.updateUser(userID, user)
    }
}

data class MyProfileUIState(
    val name: String = "",
    val jobTitle: String = "",
    val location: String = "",
    val department: String = "",
    val editableName: String = "",
    val editableJobTitle: String = "",
    val editableLocation: String = "",
    val editableDepartment: String = "",
    val editable: Boolean = false
)