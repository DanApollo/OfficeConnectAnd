package com.sky.officeconnectandroid.components

import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

private const val CALENDAR_ROWS = 6
private const val CALENDAR_COLUMNS = 7

@Composable
fun Calendar(
    bookingDate: LocalDate,
    modifier: Modifier = Modifier,
    onDayClick: (LocalDate) -> Unit,
    strokeWidth: Float = 15f,
) {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }

    var clickAnimationOffset by remember {
        mutableStateOf(initPosition(selectedDate, canvasSize))
    }

    var animationRadius by remember {
        mutableStateOf(0f)
    }

    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(onClick = {
                selectedDate = selectedDate.minusMonths(1)
                if (selectedDate.month == bookingDate.month) {
                    clickAnimationOffset=initPosition(selectedDate, canvasSize)
                    scope.launch {
                        animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                            animationRadius = value
                        }
                    }
                } else {
                    scope.launch {
                        animate(0f, 0f) { value, _ ->
                            animationRadius = value
                        }
                    }
                }

            }) {
                Text(text = "<")
            }
            Text(
                text = selectedDate.month.toString(),
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 40.sp
            )
            Button(onClick = {
                selectedDate = selectedDate.plusMonths(1)
                if (selectedDate.month == bookingDate.month) {
                    clickAnimationOffset=initPosition(selectedDate, canvasSize)
                    scope.launch {
                        animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                            animationRadius = value
                        }
                    }
                } else {
                    scope.launch {
                        animate(0f, 0f) { value, _ ->
                            animationRadius = value
                        }
                    }
                }

            }) {
                Text(text = ">")
            }
            Button(onClick = { Log.d("testLog", "$clickAnimationOffset") }) {
                Text(text = "Test")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            Arrangement.SpaceAround
        ) {
            Text(text = "Mon")
            Text(text = "Tue")
            Text(text = "Wed")
            Text(text = "Thu")
            Text(text = "Fri")
            Text(text = "Sat")
            Text(text = "Sun")
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            Log.d("testLog", "$offset")
                            val column =
                                (offset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
                            val row = (offset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1
                            val day =
                                column + (row - 1) * CALENDAR_COLUMNS - (selectedDate.withDayOfMonth(
                                    1
                                ).dayOfWeek.value - 1)
                            if (day > 0 && day <= selectedDate.lengthOfMonth()) {
                                selectedDate = selectedDate.withDayOfMonth(day)
                                onDayClick(selectedDate)
                                clickAnimationOffset = offset
                                scope.launch {
                                    animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                                        animationRadius = value
                                    }
                                }
                            }
                        }
                    )
                },
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            canvasSize = Size(canvasWidth, canvasHeight)
            val ySteps = canvasHeight / CALENDAR_ROWS
            val xSteps = canvasWidth / CALENDAR_COLUMNS

            val column = (clickAnimationOffset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
            val row = (clickAnimationOffset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1

            val path = Path().apply {
                moveTo((column - 1) * xSteps, (row - 1) * ySteps)
                lineTo(column * xSteps, (row - 1) * ySteps)
                lineTo(column * xSteps, row * ySteps)
                lineTo((column - 1) * xSteps, row * ySteps)
                close()
            }

            clipPath(path) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color.Red.copy(0.8f), Color.Red.copy(0.2f)),
                        center = clickAnimationOffset,
                        radius = animationRadius + 0.1f
                    ),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }
            drawRoundRect(
                Color.Red,
                cornerRadius = CornerRadius(25f, 25f),
                style = Stroke(
                    width = strokeWidth
                )
            )

            for (i in 1 until CALENDAR_ROWS) {
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, ySteps * i),
                    end = Offset(canvasWidth, ySteps * i),
                    strokeWidth = strokeWidth
                )
            }
            for (i in 1 until CALENDAR_COLUMNS) {
                drawLine(
                    color = Color.Red,
                    start = Offset(xSteps * i, 0f),
                    end = Offset(xSteps * i, canvasHeight),
                    strokeWidth = strokeWidth
                )
            }
            val textHeight = 17.dp.toPx()
            for (i in List(selectedDate.lengthOfMonth()) { Random.nextInt() }.indices) {
                val textPositionX =
                    xSteps * ((i + selectedDate.withDayOfMonth(1).dayOfWeek.value - 1) % CALENDAR_COLUMNS) + strokeWidth
                val textPositionY =
                    ((i + selectedDate.withDayOfMonth(1).dayOfWeek.value - 1) / CALENDAR_COLUMNS) * ySteps + textHeight + strokeWidth / 2
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${i + 1}",
                        textPositionX,
                        textPositionY,
                        Paint().apply {
                            textSize = textHeight
                            color = Color.White.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }

}

fun initPosition(selectedDate: LocalDate, canvasSize: Size): Offset {
    val day = selectedDate.dayOfMonth
    val dayOffset = day + selectedDate.withDayOfMonth(1).dayOfWeek.value - 1
    var div: Int
    var mod: Int
    if (dayOffset % CALENDAR_COLUMNS == 0) {
        div = ((dayOffset - 1) / CALENDAR_COLUMNS)
        mod = 7
    } else {
        div = dayOffset / CALENDAR_COLUMNS
        mod = dayOffset % CALENDAR_COLUMNS
    }
    val column = ((div) + 0.5f) / CALENDAR_ROWS * canvasSize.height
    val row = ((mod - 0.5f) / CALENDAR_COLUMNS) * canvasSize.width
    return Offset(row, column)
}