package com.example.hostflow.ui.theme.screens.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hostflow.R
import com.example.hostflow.data.AuthViewModel
import com.example.hostflow.navigation.ROUTE_CUSTOMER

import com.example.hostflow.navigation.ROUTE_REALTOR_LOGIN

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignupPage(navController = rememberNavController())
        }
    }
}
@Composable
fun SignupPage(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    var userName by remember {
        mutableStateOf(value = "")
    }
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }
    var confirmPassword by remember {
        mutableStateOf(value = "")
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Text(text = "Customer Register Here",
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
            contentDescription = "NCBI Logo"
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = userName,
            onValueChange =  { newUsername -> userName = newUsername },
            label = { Text(text = "Enter Username")},
            placeholder = { Text(text = "Please Enter Username")}
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange =  { newEmail -> email = newEmail },
            label = { Text(text = "Enter Email")},
            placeholder = { Text(text = "Please Enter Email")}
        )

        OutlinedTextField(
            value = password,
            onValueChange =  { newPassword -> password = newPassword },
            label = { Text(text = "Enter Password")},
            placeholder = { Text(text = "Please Enter Password")}
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange =  { newConfirmPassword -> confirmPassword = newConfirmPassword },
            label = { Text(text = "Confirm Password")},
            placeholder = { Text(text = "Please Confirm Password")}
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button( onClick = {
//            authViewModel.signup(userName,email,password,confirmPassword,navController,context)
            navController.navigate(ROUTE_CUSTOMER)
               },
            colors = ButtonDefaults.buttonColors(Color.Black)){
            Text(modifier = Modifier.padding(10.dp),
                color = Color.White,
                text = "Register")
        }
        Spacer(modifier = Modifier.height(10.dp))

        ClickableText(text = AnnotatedString("Are u a Realtor? Login here"),
            onClick = {
                navController.navigate(ROUTE_REALTOR_LOGIN) },
            style = TextStyle(color = Color.Blue,
                fontSize = 16.sp,
                textAlign = TextAlign.Center))
    }


}
