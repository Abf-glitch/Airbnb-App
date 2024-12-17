package com.example.hostflow.customers


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.example.hostflow.models.Listing
import com.google.firebase.Firebase
import com.google.firebase.database.database


@Composable
fun CustomerPage(navController: NavHostController) {
    val listings = remember { mutableStateListOf<Listing>() }

    // Fetch listings from Firebase Realtime Database
    LaunchedEffect(Unit) {
        fetchListingsFromDatabase(listings)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Available Listings",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display listings in a list
        LazyColumn {
            items(listings) { listing ->
                ListingItem(listing)
            }
        }

    }
}

@Composable
fun ListingItem(listing: Listing) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = listing.title, style = MaterialTheme.typography.headlineLarge)
            Text(text = "$${listing.price}", style = MaterialTheme.typography.bodyMedium)
            Text(text = listing.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun fetchListingsFromDatabase(listings: MutableList<Listing>) {
    val database = Firebase.database
//    val database = FirebaseDatabase.getInstance()
    val listingsRef = database.getReference("listings")

    listingsRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            listings.clear()
            for (listingSnapshot in snapshot.children) {
                val listing = listingSnapshot.getValue(Listing::class.java)
                listing?.let { listings.add(it) }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("Firebase", "Failed to read listings.", error.toException())
        }
    })
}

@Composable
fun CustomerScreen(listings: List<Listing>, onBook: (Listing) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(listings) { listing ->
            ListingItem(
                listing = listing,
                onBook = { selectedListing ->
                    onBook(selectedListing) // Navigate to Booking Page or other logic
                }
            )
        }
    }
    CustomerScreen(listings, onBook)
}

@Composable
fun ListingItem(listing: Listing, onBook: (Listing) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = listing.title, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "$${listing.price}", modifier = Modifier.padding(bottom = 8.dp))
        Button(
            onClick = { onBook(listing) }, // Pass selected listing to booking logic
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Book Now")
        }
    }
}



