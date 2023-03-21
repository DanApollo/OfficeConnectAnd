package com.sky.officeconnectandroid.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentCard(
    name: String,
    date: String,
    location: String
) {
    Card(
        modifier = Modifier
//            .clickable {  }
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            ) {
                Text(text = name)
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    Modifier
                        .size(size = 30.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .align(alignment = Alignment.TopEnd),
                horizontalAlignment = Alignment.End,
            ){
                Text(
                    text = date,
                )
                Text(
                    text = location,
                    modifier = Modifier
                )
            }
        }
    }
}