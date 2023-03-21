package com.sky.officeconnectandroid.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sky.officeconnectandroid.models.Appointment
import com.sky.officeconnectandroid.models.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AppointmentRepository {

    private val database: DatabaseReference = Firebase.database.reference

    fun updateAppointment(date: String, location: String, department: String, userID: String) {
        val appointment = mapOf(userID to true)
        val userAppointment = mapOf(date to location)
        // Two separate updateChildren calls for the same action risk inconsistencies in db updates.
        // When possible best to update to one updateChildren call updating both addresses.
        database.child("appointments").child(date).child(location).child(department).updateChildren(appointment)
        database.child("users").child(userID).child("appointments").updateChildren(userAppointment)
    }

    fun setAppointmentEventListener(userID: String, updateAppointments: (input: List<Appointment>) -> Unit) {
        val appointmentListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val appointmentList: List<Appointment> = if (snapshot.value != null) {
                    val snapshotMap = snapshot.value as MutableMap<String, String>
                    snapshotMap.mapNotNull {
                        try {
                            Appointment(
                                date = LocalDate.parse(it.key),
                                location = it.value)
                        } catch (e : DateTimeParseException) {
                            null
                        }
                    }
                } else {
                    listOf()
                }
                updateAppointments(appointmentList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.child("users").child(userID).child("appointments").addValueEventListener(appointmentListener)
    }
}