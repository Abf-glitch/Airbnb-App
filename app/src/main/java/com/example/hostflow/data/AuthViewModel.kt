package com.example.hostflow.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.empty.models.SignupModel
import com.example.hostflow.navigation.ROUTE_CUSTOMER
import com.example.hostflow.navigation.ROUTE_REALTOR
import com.example.hostflow.navigation.ROUTE_REALTOR_LOGIN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel: ViewModel() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    val regRef = FirebaseDatabase.getInstance().getReference("Users")

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun signup(
        userName: String, email: String, password: String,
        confirmPassword: String, navController: NavController,
        context: Context
    ) {
        if (userName.isBlank() || email.isBlank() || password.isBlank() ||
            confirmPassword.isBlank()
        ) {
            showToast("Please fill all the fields", context)
            return
        }
        if (password != confirmPassword) {
            showToast("Password do not match", context)
            return
        }
        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid ?: ""
                    val userData = SignupModel(
                        userName = userName,
                        email = email,
                        password = password,
                        userId = userId
                    )
                    saveUserToDatabase(userId, userData, navController, context)

                    val user = mAuth.currentUser
                    val profile = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .build()
                    user?.updateProfile(profile)?.addOnCompleteListener{
                            updateTask -> if (updateTask.isSuccessful){
                        showToast("Display name set Correctly",context)
                    }else{
                        showToast("Failed to set display name",context)
                    }
                    }
                } else {
                    _errorMessage.value = task.exception?.message
                    showToast(
                        task.exception?.message ?: "Registration failed", context
                    )
                }
            }
        signup(userName, email, password, confirmPassword, navController, context)
    }

    fun saveUserToDatabase(
        userId: String, userData: SignupModel,
        navController: NavController, context: Context
    ) {
        regRef.setValue(userData).addOnCompleteListener { regRef ->
            if (regRef.isSuccessful) {
                showToast("User Successfully Registered", context)
                navController.navigate(ROUTE_CUSTOMER)
            } else {
                _errorMessage.value = regRef.exception?.message
                showToast(
                    regRef.exception?.message ?: "Database error",
                    context
                )
            }
        }

    }

    public fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun login(
        email: String,
        password: String,
        navController: NavController,
        context: Context
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Email and password required", context)
            return
        }

        var isLoadingValue = true // Changed from `val` to `var`

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoadingValue = false // Reassigning is now possible
                if (task.isSuccessful) {
                    showToast("User successfully logged in", context)
                    navController.navigate(ROUTE_REALTOR)
                } else {
                    val errorMessage = task.exception?.message
                    showToast(errorMessage ?: "Login failed", context)
                }
            }
        login(email, password, navController, context)
    }

    fun logout(navController: NavController,context: Context){
        showToast("Logged Out Successfully",context)
        navController.navigate(ROUTE_REALTOR_LOGIN)
    }
}