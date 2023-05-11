package com.sky.officeconnectandroid.animatedsplash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sky.officeconnectandroid.ui.theme.Purple700
import com.sky.officeconnectandroid.R
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplash(
    onNavToLoginPage:() -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    // When startAnimation is changed, alphaAnim.value begins animation.
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )
    // Splash screen will run animation on render, then navigate to Login.
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        onNavToLoginPage.invoke()
    }
    // Create fade in effect with alphaAnim.value.
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(10,22,75),
                        Color(10,20,130)
                    )
                )
            )
            .fillMaxSize()
            .padding(horizontal = 100.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sky_group_logo),
                contentDescription = null,
                alpha = alpha
            )
            Text(
                text = "Office Connect",
                color = Color.White.copy(alpha = alpha),
            )
        }
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}