package com.sky.officeconnectandroid.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sky.officeconnectandroid.models.User

const val USERS_REF = "users"

class UserRepository {
    fun user() = Firebase.auth.currentUser

    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val database:DatabaseReference = Firebase.database.reference

    fun addUser(name: String, location: String, department: String, jobTitle: String) {
        val user = User(name, location, department, jobTitle)

        database.child(USERS_REF).child(getUserId()).setValue(user)
    }

    fun setUsersEventListener(eventListener: ValueEventListener) {
        database.child(USERS_REF).addValueEventListener(eventListener)
    }
}