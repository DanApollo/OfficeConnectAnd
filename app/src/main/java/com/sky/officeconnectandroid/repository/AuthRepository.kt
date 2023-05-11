package com.sky.officeconnectandroid.repository

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.os.bundleOf
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    fun getUser(): FirebaseUser = Firebase.auth.currentUser!!

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    fun getUserEmail(): String = Firebase.auth.currentUser!!.email!!

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
    suspend fun reAuthUser(
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val email2 = async { getUserEmail() }
        val credential = EmailAuthProvider.getCredential(email2.await(), password)
        getUser().reauthenticate(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    suspend fun deleteUser(
        onComplete: (Boolean) -> Unit
    ): Unit = withContext(Dispatchers.IO) {
        getUser().delete().addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }
}