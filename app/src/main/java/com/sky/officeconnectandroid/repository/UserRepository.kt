package com.sky.officeconnectandroid.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.sky.officeconnectandroid.models.User

const val USERS_REF = "users"

class UserRepository {

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val database: DatabaseReference = Firebase.database.reference

    fun addUser(name: String, location: String, department: String, jobTitle: String) {
        val user = User(name, location, department, jobTitle)

        database.child(USERS_REF).child(getUserId()).setValue(user)
    }

    fun setUserEventListener(userID: String, updateUser: (input: User?) -> Unit) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                updateUser(user)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.child(USERS_REF).child(userID).addValueEventListener(userListener)
    }
}