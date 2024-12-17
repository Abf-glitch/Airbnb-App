package com.example.hostflow.data


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.hostflow.models.Listing
import com.example.hostflow.navigation.ROUTE_CUSTOMER
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListingViewmodel(): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    @SuppressLint("SuspiciousIndentation")
    fun createListing(
        title: String,
        price: String,
        description: String,
        navController: NavController,
        context: Context
    ) {
        val id = System.currentTimeMillis().toString()
        val dbRef = FirebaseDatabase.getInstance().getReference("listings/$id")


        val listingData = Listing(title,price,description,id)
        dbRef.setValue(listingData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Listing added successfully", context)

            } else {
                showToast("Listing not added successfully", context)
                navController.navigate(ROUTE_CUSTOMER)
            }
        }
    }
    fun viewListing(list: MutableState<Listing>,
                    lists:SnapshotStateList<Listing>, context: Context): SnapshotStateList<Listing>
    {
        val ref = FirebaseDatabase.getInstance().getReference().child("listings")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                lists.clear()
                for (snap in snapshot.children){
                    val value = snap.getValue(Listing::class.java)
                    list.value = value!!
                    lists.add(value)
                }
            }
            override fun onCancelled(error: DatabaseError){
                showToast("Failed to fetch lists", context)
            }
        })
        return lists
    }
    fun updateList(
        context: Context,
        navController: NavController,
        title: String,
        price: String,
        description: String,
        id: String
    ){
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("listings/$id")
        val updatedList = Listing(title,price,description)

        databaseReference.setValue(updatedList)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    showToast("List Updated Successfully",context)
                }else{
                    showToast("Record update failed",context)
                }
            }
    }
    fun deleteList(context: Context,id: String,navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete List")
            .setMessage("Are you sure you want to delete this list")
            .setPositiveButton("Yes"){_, _ ->
                val databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("listings/$id")
                databaseReference.removeValue().addOnCompleteListener{
                        task ->
                    if (task.isSuccessful){
                        showToast("List deleted Successfully",context)
                    }else{
                        showToast("List not deleted", context)
                    }
                }
            }
            .setNegativeButton("No"){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    public fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
