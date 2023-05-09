package com.sky.officeconnectandroid.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun SkyColourText(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                val brush = Brush.horizontalGradient(
                    listOf(
                        Color(245, 100, 0),
                        Color(255, 0, 0),
                        Color(181, 0, 125),
                        Color(33, 66, 156),
                        Color(0, 113, 255)
                    )
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(brush, blendMode = BlendMode.SrcAtop)
                }
            }
    )
}