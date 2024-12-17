package com.example.hostflow.ui.theme.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import com.example.hostflow.R
import com.example.hostflow.data.ListingViewmodel


@Composable
fun RealtorPage(navController: NavController) {
    var showAddListingDialog by remember { mutableStateOf(false) }

    if (showAddListingDialog) {
        AddListingDialog(
            onDismiss = { showAddListingDialog = false },
            onAddListing = { name, price, description ->
                addListingToDatabase(name, price, description)
                showAddListingDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.background),
            contentDescription = "DashBoard Background",
            contentScale = ContentScale.FillBounds)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Realtor Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { showAddListingDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Listing")
        }
    }
}

@Composable
fun AddListingDialog(onDismiss: () -> Unit, onAddListing: (String, String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    val navController = rememberNavController()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Listing") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Property Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val listRepository = ListingViewmodel()
                    listRepository.createListing(
                        title = title,
                        price = price,
                        description = description,
                        navController = navController,
                        context = context
                    )


                }
            ) {
                Text("Add Listing")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


fun addListingToDatabase(name: String, price: String, description: String) {
    val database = FirebaseDatabase.getInstance()
    val listingsRef = database.getReference("listings")

    val listingId = listingsRef.push().key // Generate a unique ID
    val listing = Listing(name, price, description)

    listingId?.let {
        listingsRef.child(it).setValue(listing)
            .addOnSuccessListener {
                Log.d("Firebase", "Listing added successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error adding listing", e)
            }
    }
}

data class Listing(val name: String, val price: String, val description: String)