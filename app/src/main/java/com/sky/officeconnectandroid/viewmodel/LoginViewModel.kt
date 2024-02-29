package com.sky.officeconnectandroid.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    val hasUser: Boolean
        get() = authRepository.hasUser()

    var loginUIState by mutableStateOf(LoginUIState())
        private set

    fun onEmailChange(email: String) {
        loginUIState = loginUIState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginUIState = loginUIState.copy(password = password)
    }

    fun onEmailSignUpChange(email: String) {
        loginUIState = loginUIState.copy(emailSignUp = email)
    }

    fun onNameSignUpChange(name: String) {
        loginUIState = loginUIState.copy(nameSignUp = name)
    }

    fun onJobTitleSignUpChange(jobTitle: String) {
        loginUIState = loginUIState.copy(jobTitleSignUp = jobTitle)
    }

    fun onLocationSignUpChange(location: String) {
        loginUIState = loginUIState.copy(locationSignUp = location)
    }

    fun onDepartmentSignUpChange(department: String) {
        loginUIState = loginUIState.copy(departmentSignUp = department)
    }

    fun onPasswordSignUpChange(password: String) {
        loginUIState = loginUIState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        loginUIState = loginUIState.copy(confirmPasswordSignUp = password)
    }

    fun validateLoginForm() =
        loginUIState.email.isNotBlank() &&
                loginUIState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginUIState.emailSignUp.isNotBlank() &&
                loginUIState.nameSignUp.isNotBlank() &&
                loginUIState.jobTitleSignUp.isNotBlank() &&
                loginUIState.locationSignUp.isNotBlank() &&
                loginUIState.departmentSignUp.isNotBlank() &&
                loginUIState.passwordSignUp.isNotBlank() &&
                loginUIState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            loginUIState = loginUIState.copy(isLoading = true)
            if (!validateSignUpForm()) {
                throw java.lang.IllegalArgumentException("Fields cannot be left empty.")
            }
            if (loginUIState.passwordSignUp !=
                loginUIState.confirmPasswordSignUp
            ) {
                throw java.lang.IllegalArgumentException(
                    "Passwords do not match"
                )
            }
            // No errors occurred, update signUpError state.
            loginUIState = loginUIState.copy(signUpError = null)
            // FirebaseAuth API call
            authRepository.createUser(
                loginUIState.emailSignUp,
                loginUIState.passwordSignUp
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Successful login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Firebase RealtimeDB API call
                    userRepository.addUser(
                        loginUIState.nameSignUp,
                        loginUIState.locationSignUp,
                        loginUIState.departmentSignUp,
                        loginUIState.jobTitleSignUp
                    )
                    loginUIState = loginUIState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState = loginUIState.copy(isSuccessLogin = false)
                }
            }
        } catch (e: Exception) {
            loginUIState = loginUIState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            loginUIState = loginUIState.copy(isLoading = true)
            if (!validateLoginForm()) {
                throw java.lang.IllegalArgumentException("email and password cannot be empty.")
            }
            // No errors occurred, update signUpError state.
            loginUIState = loginUIState.copy(loginError = null)
            // FirebaseAuth API call
            authRepository.login(
                loginUIState.email,
                loginUIState.password
            ) { isSuccessful ->
                loginUIState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Successful login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState.copy(isSuccessLogin = false)
                }
            }
        } catch (e: Exception) {
            loginUIState = loginUIState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }
    }
}

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val emailSignUp: String = "",
    val nameSignUp: String = "",
    val jobTitleSignUp: String = "",
    val locationSignUp: String = "",
    val departmentSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)