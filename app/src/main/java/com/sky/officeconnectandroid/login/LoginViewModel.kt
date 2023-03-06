package com.sky.officeconnectandroid.login

import android.content.Context
import android.util.Log
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
    val currentUser = authRepository.currentUser

    val hasUser: Boolean
        get() = authRepository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(email: String) {
        loginUiState = loginUiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }

    fun onEmailSignUpChange(email: String) {
        loginUiState = loginUiState.copy(emailSignUp = email)
    }

    fun onNameSignUpChange(name: String) {
        loginUiState = loginUiState.copy(nameSignUp = name)
    }

    fun onJobTitleSignUpChange(jobTitle: String) {
        loginUiState = loginUiState.copy(jobTitleSignUp = jobTitle)
    }

    fun onLocationSignUpChange(location: String) {
        loginUiState = loginUiState.copy(locationSignUp = location)
    }

    fun onDepartmentSignUpChange(department: String) {
        loginUiState = loginUiState.copy(departmentSignUp = department)
    }

    fun onPasswordSignUpChange(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    private fun validateLoginForm() =
        loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginUiState.emailSignUp.isNotBlank() &&
                loginUiState.nameSignUp.isNotBlank() &&
                loginUiState.jobTitleSignUp.isNotBlank() &&
                loginUiState.locationSignUp.isNotBlank() &&
                loginUiState.departmentSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            loginUiState = loginUiState.copy(isLoading = true)
            if (!validateSignUpForm()) {
                throw java.lang.IllegalArgumentException("Fields cannot be left empty.")
            }
            if (loginUiState.passwordSignUp !=
                loginUiState.confirmPasswordSignUp
            ) {
                throw java.lang.IllegalArgumentException(
                    "Passwords do not match"
                )
            }
            // No errors occurred, update signUpError state.
            loginUiState = loginUiState.copy(signUpError = null)
            // FirebaseAuth API call
            authRepository.createUser(
                loginUiState.emailSignUp,
                loginUiState.passwordSignUp
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Successful login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Firebase RealtimeDB API call
                    userRepository.addUser(
                        loginUiState.nameSignUp,
                        loginUiState.locationSignUp,
                        loginUiState.departmentSignUp,
                        loginUiState.jobTitleSignUp
                    )
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            loginUiState = loginUiState.copy(isLoading = true)
            if (!validateLoginForm()) {
                throw java.lang.IllegalArgumentException("email and password cannot be empty.")
            }
            // No errors occurred, update signUpError state.
            loginUiState = loginUiState.copy(loginError = null)
            // FirebaseAuth API call
            authRepository.login(
                loginUiState.email,
                loginUiState.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Successful login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
}

data class LoginUiState(
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