package com.sky.officeconnectandroid.myprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository

class MyProfileViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {
    val userID: String
        get() = authRepository.getUserId()

    var myProfileUIState by mutableStateOf(MyProfileUIState())
        private set

    private fun getUserListener() {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.child(userID).getValue<User>()
                myProfileUIState = myProfileUIState.copy(
                    name = user?.name ?: "",
                    jobTitle = user?.jobTitle ?: "",
                    location = user?.location ?: "",
                    department = user?.department ?: ""
                )
                Log.d("dataTag", user?.name ?: "")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        userRepository.setUsersEventListener(userListener)
    }

    fun onNameChange(name: String) {
        myProfileUIState = myProfileUIState.copy(name = name)
    }

    fun onJobTitleChange(jobTitle: String) {
        myProfileUIState = myProfileUIState.copy(jobTitle = jobTitle)
    }

    fun onLocationChange(location: String) {
        myProfileUIState = myProfileUIState.copy(location = location)
    }

    fun onDepartmentChange(department: String) {
        myProfileUIState = myProfileUIState.copy(department = department)
    }
}

data class MyProfileUIState(
    val name: String = "",
    val jobTitle: String = "",
    val location: String = "",
    val department: String = "",
    val editable: Boolean = false
)