package com.sky.officeconnectandroid.myprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sky.officeconnectandroid.components.SkyButton
import com.sky.officeconnectandroid.components.SkyColourText

@Composable
fun MyProfile(
    myProfileViewModel: MyProfileViewModel,
    onNavToHomePage: () -> Unit,
    onNavToMyOfficeDays: () -> Unit,
    onNavToLoginPage: () -> Unit
) {
    val myProfileUIState = myProfileViewModel.myProfileUIState
    val editedName = myProfileUIState.user.name != myProfileUIState.editableName
    val editedJobTitle = myProfileUIState.user.jobTitle != myProfileUIState.editableJobTitle
    val editedLocation = myProfileUIState.user.location != myProfileUIState.editableLocation
    val editedDepartment = myProfileUIState.user.department != myProfileUIState.editableDepartment
    val edited = editedName || editedJobTitle || editedLocation || editedDepartment
    var logOutPopup by remember {
        mutableStateOf(false)
    }
    var deleteAccountPopup by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Icon(
                imageVector =
                if (!edited)
                    Icons.Default.ArrowBack
                else
                    Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (!edited)
                            onNavToHomePage.invoke()
                        else
                            myProfileViewModel.onCancelEdit()
                    }
                    .align(Alignment.CenterStart)
                    .padding(10.dp)
            )
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            if (!edited) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            logOutPopup = true
                        }
                        .align(Alignment.CenterEnd)
                        .padding(10.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Divider()
            ProfileRow(
                edited = editedName,
                label = "Name",
                value = myProfileUIState.editableName,
                onValueChange =  myProfileViewModel::onNameChange
            )
            Divider(
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(alignment = Alignment.End)
            )
            ProfileRow(
                edited = editedJobTitle,
                label = "Job Title",
                value = myProfileUIState.editableJobTitle,
                onValueChange =  myProfileViewModel::onJobTitleChange
            )
            Divider(
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(alignment = Alignment.End)
            )
            ProfileRow(
                edited = editedLocation,
                label = "Location",
                value = myProfileUIState.editableLocation,
                onValueChange =  myProfileViewModel::onLocationChange
            )
            Divider(
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(alignment = Alignment.End)
            )
            ProfileRow(
                edited = editedDepartment,
                label = "Department",
                value = myProfileUIState.editableDepartment,
                onValueChange =  myProfileViewModel::onDepartmentChange
            )
            Divider()
        }
        if (!edited) {
            Button(
                onClick = { onNavToMyOfficeDays.invoke() },
                border = null,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(45.dp)
                    .padding(top = 5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Office Days",
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        } else {
            SkyButton(
                onClick = { myProfileViewModel.onUpdateUser() },
                text = "Done",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(45.dp)
                    .padding(top = 5.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Delete Profile",
                color = Color.Red,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .clickable {
//                        myProfileViewModel.onDeleteUser(context)
//                        onNavToLoginPage.invoke()
                        deleteAccountPopup = true
                    }
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
            )
        }
        if (logOutPopup) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { logOutPopup = false}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(100, 100, 100).copy(0.8f)),
                    ){
                    Column(modifier = Modifier
                        .size(width = 300.dp, height = 400.dp)
                        .align(Alignment.Center)
                        .background(color = Color.White)
                    ) {
                        SkyColourText(
                            text = "Log out",
                            modifier = Modifier
                        )
                        Button(
                            onClick = {
                                Firebase.auth.signOut()
                                onNavToLoginPage.invoke()
                            }) {
                            Text(text = "Log out")
                        }
                        Button(onClick = { logOutPopup = false }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
        if (deleteAccountPopup) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { deleteAccountPopup = false}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(100, 100, 100).copy(0.8f)),
                ){
                    Column(modifier = Modifier
                        .size(width = 300.dp, height = 400.dp)
                        .align(Alignment.Center)
                        .background(color = Color.White)
                    ) {
                        SkyColourText(
                            text = "Delete Account",
                            modifier = Modifier
                        )
                        TextField(
                            value = myProfileUIState.password,
                            onValueChange = { myProfileViewModel.onPasswordChange(it) }
                        )
                        Text(text = myProfileUIState.password)
                        Button(
                            onClick = {
                                myProfileViewModel.onDeleteUser(myProfileUIState.password)
                            }) {
                            Text(text = "Delete")
                        }
                        Button(onClick = { deleteAccountPopup = false }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ProfileRow(
    edited: Boolean,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp)
            .background(color = if (edited) Color.Red.copy(alpha = 0.3f) else Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth(0.3f)
        )
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (value.isEmpty()) {
                        Text(
                            text = label,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
@Preview
fun ProfileRowPreview() {
    ProfileRow(
        edited = false,
        label = "Name",
        value = "Paul",
        onValueChange = {}
    )
}

@Composable
@Preview
fun ProfileRowEditedPreview() {
    ProfileRow(
        edited = true,
        label = "Name",
        value = "Paul",
        onValueChange = {}
    )
}