package com.sky.officeconnectandroid.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentCard(
    date: String,
    location: String
) {
    Column(modifier = Modifier.padding(5.dp)){
        Text(
            text = date,
            modifier = Modifier
        )
        Text(
            text = location,
            modifier = Modifier
        )
    }
}