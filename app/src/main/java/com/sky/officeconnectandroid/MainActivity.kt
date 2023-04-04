package com.sky.officeconnectandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sky.officeconnectandroid.home.HomeViewModel
import com.sky.officeconnectandroid.login.LoginViewModel
import com.sky.officeconnectandroid.myofficedays.MyOfficeDaysViewModel
import com.sky.officeconnectandroid.myprofile.MyProfileViewModel
import com.sky.officeconnectandroid.navigation.Navigation
import com.sky.officeconnectandroid.newofficeday.NewOfficeDayViewModel
import com.sky.officeconnectandroid.ui.theme.OfficeConnectAndTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val myProfileViewModel = viewModel(modelClass = MyProfileViewModel::class.java)
            val newOfficeDayViewModel = viewModel(modelClass = NewOfficeDayViewModel::class.java)
            val myOfficeDaysViewModel = viewModel(modelClass = MyOfficeDaysViewModel::class.java)
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            OfficeConnectAndTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(
                        loginViewModel = loginViewModel,
                        myProfileViewModel = myProfileViewModel,
                        newOfficeDayViewModel = newOfficeDayViewModel,
                        myOfficeDaysViewModel = myOfficeDaysViewModel,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OfficeConnectAndTheme {
        Greeting("Android")
    }
}