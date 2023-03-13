package com.sky.officeconnectandroid.models

data class User(
    val name:String = "",
    val location:String = "",
    val department:String = "",
    val jobTitle:String = "",
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "location" to location,
            "department" to department,
            "jobTitle" to jobTitle
        )
    }
}
