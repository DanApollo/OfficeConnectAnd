package com.sky.officeconnectandroid.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AppointmentRepository {

    private val database: DatabaseReference = Firebase.database.reference

    fun addAppointment(date: String, location: String, department: String, userID: String) {
        val appointment = mapOf(userID to true)
        database.child("appointments").child(date).child(location).child(department).setValue(appointment)
    }
}