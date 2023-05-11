package com.sky.officeconnectandroid.view.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky.officeconnectandroid.models.AppointmentCardModel

@Composable
fun AppointmentCard(
    isUser: Boolean,
    appointment: AppointmentCardModel,
    onDelete: () -> Unit,
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
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(5.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    Modifier
                        .size(size = 35.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp)
                ) {
                    Text(text = appointment.name)
                    Text(
                        text = "${appointment.department} - ${appointment.jobTitle}",
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isUser) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = appointment.location,
                            fontSize = 12.sp
                        )
                        Text(
                            text = appointment.date,
                            fontSize = 12.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        Modifier
                            .padding(10.dp)
                            .clickable(onClick = { onDelete.invoke() })
                    )
                } else {
                    Text(text = appointment.location)
                }
            }
        }
    }
}

@Composable
@Preview
fun AppointmentCardPreview() {
    AppointmentCard(
        isUser = true,
        appointment = AppointmentCardModel(
            name = "Daniel Hope",
            location = "Osterley",
            department = "Sky Go",
            jobTitle = "Software Development Apprentice",
            date = "13th of July",
        ),
        onDelete = {}
    )
}

@Composable
@Preview
fun AppointmentCardIsUserPreview() {
    AppointmentCard(
        isUser = false,
        appointment = AppointmentCardModel(
            name = "Bee",
            location = "Osterley",
            department = "Sky Go",
            jobTitle = "Software",
            date = "13th of July",
        ),
        onDelete = {}
    )
}