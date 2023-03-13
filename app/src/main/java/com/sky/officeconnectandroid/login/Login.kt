package com.sky.officeconnectandroid.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sky.officeconnectandroid.R

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(10, 22, 75),
                        Color(10, 20, 130)
                    )
                )
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 125.dp)
                .padding(top = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sky_group_logo),
                contentDescription = null,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(28.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
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
                    .padding(vertical = 10.dp)
            )
            if (isError) {
                Text(
                    text = loginUiState?.loginError ?: "unknown error",
                    color = Color.Red
                )
            }
            Text(text = "Sign in with your Office Connect account.")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 4.dp),
                value = loginUiState?.email ?: "",
                onValueChange = { loginViewModel?.onEmailChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Email"
                    )
                },
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp),
                value = loginUiState?.password ?: "",
                onValueChange = { loginViewModel?.onPasswordChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Password"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )
            Button(
                onClick = { loginViewModel?.loginUser(context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 40.dp)
                    .padding(horizontal = 16.dp),
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
                        text = "Continue",
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have an Account?")
                Spacer(modifier = Modifier.size(8.dp))
                TextButton(onClick = { onNavToSignUpPage.invoke() }) {
                    Text(text = "Sign Up")
                }
            }
            if (loginUiState?.isLoading == true) {
                CircularProgressIndicator()
            }
            LaunchedEffect(key1 = loginViewModel?.hasUser) {
                if (loginViewModel?.hasUser == true) {
                    onNavToHomePage.invoke()
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(10, 22, 75),
                        Color(10, 20, 130)
                    )
                )
            )
            .padding(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Let's get started",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
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
            if (isError) {
                Text(
                    text = loginUiState?.signUpError ?: "unknown error",
                    color = Color.Red
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.emailSignUp ?: "",
                onValueChange = { loginViewModel?.onEmailSignUpChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Email"
                    )
                },
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.nameSignUp ?: "",
                onValueChange = { loginViewModel?.onNameSignUpChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Name"
                    )
                },
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.jobTitleSignUp ?: "",
                onValueChange = { loginViewModel?.onJobTitleSignUpChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Job Title"
                    )
                },
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.locationSignUp ?: "",
                onValueChange = { loginViewModel?.onLocationSignUpChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Location"
                    )
                },
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.departmentSignUp ?: "",
                onValueChange = { loginViewModel?.onDepartmentSignUpChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Department"
                    )
                },
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.passwordSignUp ?: "",
                onValueChange = { loginViewModel?.onPasswordSignUpChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Password"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = loginUiState?.confirmPasswordSignUp ?: "",
                onValueChange = { loginViewModel?.onConfirmPasswordChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = "Confirm Password"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )
            Button(
                onClick = { loginViewModel?.createUser(context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 40.dp)
                    .padding(horizontal = 16.dp),
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
                        text = "Sign Up",
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Already have an Account?")
                TextButton(onClick = { onNavToLoginPage.invoke() }) {
                    Text(text = "Sign In")
                }
            }
            if (loginUiState?.isLoading == true) {
                CircularProgressIndicator()
            }
            LaunchedEffect(key1 = loginViewModel?.hasUser) {
                if (loginViewModel?.hasUser == true) {
                    onNavToHomePage.invoke()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    LoginScreen(
        onNavToHomePage = {},
        onNavToSignUpPage = {},
    )
}

@Preview(showSystemUi = true)
@Composable
fun PrevSignUpScreen() {
    SignUpScreen(
        onNavToHomePage = {},
        onNavToLoginPage = {},
    )
}