package com.sky.officeconnectandroid.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentCard(
    name: String,
    date: String,
    location: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .height(75.dp),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
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
            ){
                Text(
                    text = date,
                )
                Text(
                    text = location,
                    modifier = Modifier
                )
            }
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                Modifier
                    .clickable(onClick = { onDelete.invoke() })
            )
        }
    }
}

@Composable
@Preview
fun AppointmentCardPreview() {
    AppointmentCard(
        name = "",
        date = "",
        location = "",
        onDelete = {}
    )
}