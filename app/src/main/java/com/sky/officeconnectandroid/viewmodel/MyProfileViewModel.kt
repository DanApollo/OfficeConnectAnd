package com.sky.officeconnectandroid.myprofile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import kotlinx.coroutines.launch

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
            user = input?: User(),
            editableName = input?.name ?: "",
            editableJobTitle = input?.jobTitle ?: "",
            editableLocation = input?.location ?: "",
            editableDepartment = input?.department ?: "",
        )
    }

    fun setUserEventListener() {
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

    fun onEmailChange(email: String) {
        myProfileUIState = myProfileUIState.copy(email = email)
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
    fun onDeleteUser(
        context: Context,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = viewModelScope.launch {
        try {
            authRepository.reAuthUser(password) { isSuccessful ->
                if (isSuccessful) {
                    userRepository.deleteUser(userID, myProfileUIState.user)
                    Toast.makeText(
                        context,
                        "Account data deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Failed login.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            authRepository.deleteUser { isSuccessful ->
                if (isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed delete",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Log.d("testLog", userID)
            Log.d("testLog", "error: ${e.localizedMessage}")
        }
    }
}

data class MyProfileUIState(
    val user: User = User(),
    val editableName: String = "",
    val editableJobTitle: String = "",
    val editableLocation: String = "",
    val editableDepartment: String = "",
    val email: String = "",
    val password: String = ""
)