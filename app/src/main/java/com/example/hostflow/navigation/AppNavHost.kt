package com.example.hostflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hostflow.customers.CustomerPage
import com.example.hostflow.ui.theme.screens.login.RealtorPage
import com.example.hostflow.ui.theme.screens.signup.SignupPage
import com.example.myapp.ui.theme.screen.login.RealtorLoginScreen

@Composable
fun AppNavHost(
    navController:NavHostController = rememberNavController(),
    startDestination:String = ROUTE_REGISTER
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_HOME) {
            {
                navController.navigate(ROUTE_HOME) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            }
        }
        composable(ROUTE_REGISTER) { SignupPage(navController) }
        composable(ROUTE_REALTOR_LOGIN) { RealtorLoginScreen(navController) }
        composable(ROUTE_CUSTOMER) { CustomerPage(navController) }
        composable(ROUTE_REALTOR) {
            RealtorPage(navController)} }

    }



