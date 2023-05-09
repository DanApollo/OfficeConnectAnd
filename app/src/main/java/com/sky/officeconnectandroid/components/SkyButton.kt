package com.sky.officeconnectandroid.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SkyButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        val colorStops = arrayOf(
            0f to Color(255, 255, 255).copy(0.0f),
            0.5f to Color(255, 255, 255).copy(0.15f),
            0.51f to Color.Transparent,
        )
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(colorStops = colorStops),
                    shape = RoundedCornerShape(4.dp)
                )
                .fillMaxSize()
        ){
            Text(
                text = text,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
            )
        }
    }
}