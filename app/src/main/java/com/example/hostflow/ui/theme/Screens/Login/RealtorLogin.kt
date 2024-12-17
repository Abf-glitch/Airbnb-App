package com.example.myapp.ui.theme.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hostflow.R
import com.example.hostflow.data.AuthViewModel
import com.example.hostflow.navigation.ROUTE_REALTOR
import com.example.hostflow.navigation.ROUTE_REALTOR_LOGIN
import com.example.hostflow.navigation.ROUTE_REGISTER

@Composable
fun RealtorLoginScreen(navController: NavController){
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Please Realtor Login Here",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Black)
                .padding(20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Image(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .height(80.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Equity logo")

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {newEmail -> email = newEmail},
            label = {Text(text = "Enter Email")},
            placeholder = {Text(text = "Please enter Email")},
        )

        OutlinedTextField(
            value = password ,
            onValueChange = {newPassword -> password = newPassword},
            label = {Text(text = "Enter Password")},
            placeholder = {Text(text = "Please enter Password")},
        )

        Spacer(modifier = Modifier.height(10.dp))

        val defaultUsername = "abaswarsame@gmail.com"
        val defaultPassword = "1234567"

        Button(onClick = {
            if (email == defaultUsername && password == defaultPassword) {
                // Navigate to the next screen
                navController.navigate(ROUTE_REALTOR)
                println("Login Successful")
            } else {
                println("Invalid username or password")
            }
        }, colors = ButtonDefaults.buttonColors(Color.Black)) {
            Text(
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                text = "LOGIN HERE"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ClickableText(text = AnnotatedString("Are you a Renter? Register here"),
            onClick = {
                navController.navigate(ROUTE_REGISTER)
            },
            style = TextStyle(color = Color.Blue,
                fontSize = 16.sp,
                textAlign = TextAlign.Center))

    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(rememberNavController())
//}