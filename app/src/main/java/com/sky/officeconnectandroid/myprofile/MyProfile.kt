package com.sky.officeconnectandroid.myprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyProfile(
    myProfileViewModel: MyProfileViewModel? = null,
    onNavToHomePage: () -> Unit
) {
    val myProfileUIState = myProfileViewModel?.myProfileUIState
    if (!myProfileUIState?.editable!!) {
        myProfileViewModel.updateUserData()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (!myProfileUIState.editable) {
                Button(onClick = { onNavToHomePage.invoke() }) {
                    Text(text = "Back")
                }
                Text(text = "My Profile")
            } else {
                Button(onClick = { myProfileViewModel.onEditableToggle() }) {
                    Text(text = "Cancel")
                }
                Text(text = "Edit Profile")
                Button(onClick = {
                    myProfileViewModel.onUpdateUser()
                    myProfileViewModel.onEditableToggle()
                }) {
                    Text(text = "Done")
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Name: ")
                if (!myProfileUIState.editable) {
                    Text(text = myProfileUIState.name)
                } else {
                    TextField(value = myProfileUIState.editableName,
                        onValueChange = { myProfileViewModel.onNameChange(it) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Job Title: ")
                if (!myProfileUIState.editable) {
                    Text(text = myProfileUIState.jobTitle)
                } else {
                    TextField(value = myProfileUIState.editableJobTitle,
                        onValueChange = { myProfileViewModel.onJobTitleChange(it) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Location: ")
                if (!myProfileUIState.editable) {
                    Text(text = myProfileUIState.location)
                } else {
                    TextField(value = myProfileUIState.editableLocation,
                        onValueChange = { myProfileViewModel.onLocationChange(it) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Department: ")
                if (!myProfileUIState.editable) {
                    Text(text = myProfileUIState.department)
                } else {
                    TextField(value = myProfileUIState.editableDepartment,
                        onValueChange = { myProfileViewModel.onDepartmentChange(it) })
                }
            }
        }
        if (!myProfileUIState.editable) {
            Button(onClick = { myProfileViewModel.onEditableToggle() }) {
                Text(text = "Edit")
            }
        }
    }
}

