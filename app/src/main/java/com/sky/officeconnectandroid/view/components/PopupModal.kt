package com.sky.officeconnectandroid.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun PopupModal(
    toggleOff: () -> Unit,
    title: String,
    children: @Composable () -> Unit
) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { toggleOff() },
        properties = PopupProperties(focusable = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(200, 200, 200).copy(0.8f)),
        ){
            Card(modifier = Modifier
                .size(width = 300.dp, height = 150.dp)
                .align(Alignment.Center),
                elevation = 10.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(5.dp)
                                .clickable { toggleOff() }
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        children()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PopupModalPreview() {
    PopupModal(
        toggleOff = {},
        title = "Preview"
    ) {
    }
}