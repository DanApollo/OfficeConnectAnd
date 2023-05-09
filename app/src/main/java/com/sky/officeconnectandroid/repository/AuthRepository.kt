package com.sky.officeconnectandroid.repository

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.os.bundleOf
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    private val TAG: String = "AuthRepository"
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

        val user: FirebaseUser? = Firebase.auth.currentUser
//
    fun reAuthUser(password: String, onComplete: (Boolean) -> Unit) {
        val email = user?.email
        val credential = EmailAuthProvider
            .getCredential(email!!, password)
        user?.reauthenticate(credential)
            ?.addOnCompleteListener{
                if (it.isSuccessful) {
                    Log.d(TAG, "Successful Re-Auth")
                    onComplete.invoke(true)
                } else {
                    Log.e(TAG, "ERROR $it")
                    onComplete.invoke(false)
                }
            }
    }

//    suspend fun deleteUser(onComplete: (Boolean) -> Unit) {
//        withContext(Dispatchers.IO) {
//            val user = Firebase.auth.currentUser
//            if (user != null) {
//                try {
//                    user.delete().await()
//                    onComplete(true)
//                } catch (e: Exception) {
//                    Log.e("testLog", "Error deleting user: ${e.message}")
//                    onComplete(false)
//                }
//            } else {
//                Log.e("testLog", "No user signed in")
//                onComplete(false)
//            }
//        }
//    }
}