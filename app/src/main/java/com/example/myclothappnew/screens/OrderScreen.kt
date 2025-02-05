package com.example.myclothappnew.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.myclothappnew.viewModels.OrderViewModel

@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    val context = LocalContext.current

    // Permission launcher for contacts
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Contact picker launcher
    val pickContactLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val data = result.data
            if (result.resultCode == android.app.Activity.RESULT_OK && data != null) {
                val contactUri = data.data
                contactUri?.let { uri ->
                    val cursor = context.contentResolver.query(
                        uri,
                        arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                        null,
                        null,
                        null
                    )
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val numberIndex =
                                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            if (numberIndex != -1) {
                                val phoneNumber = it.getString(numberIndex)
                                viewModel.phoneNumber.value = phoneNumber // Update the phone number in view model
                            }
                        }
                    }
                }
            }
        }
    )

    // Function to check permission and open contacts
    fun openContacts() {
        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            pickContactLauncher.launch(intent) // Launch the contact picker
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) // Request permission
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.name.value = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = viewModel.email.value,
                    onValueChange = { viewModel.email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Phone number field that opens contacts
                TextField(
                    value = viewModel.phoneNumber.value,
                    onValueChange = { viewModel.phoneNumber.value = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { openContacts() } // Open contacts when clicked
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Payment Method:")

                Row {
                    RadioButton(
                        selected = viewModel.paymentMethod.value == "Cash",
                        onClick = { viewModel.paymentMethod.value = "Cash" }
                    )
                    Text("Cash")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = viewModel.paymentMethod.value == "Card",
                        onClick = { viewModel.paymentMethod.value = "Card" }
                    )
                    Text("Card")
                }

                if (viewModel.paymentMethod.value == "Card") {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = viewModel.cardNumber.value,
                        onValueChange = { viewModel.cardNumber.value = it },
                        label = { Text("Card Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = viewModel.expiryDate.value,
                        onValueChange = { viewModel.expiryDate.value = it },
                        label = { Text("Expiry Date (MM/YY)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = viewModel.cvv.value,
                        onValueChange = { viewModel.cvv.value = it },
                        label = { Text("CVV") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                val allFieldsFilled =
                    viewModel.name.value.isNotEmpty() && viewModel.email.value.isNotEmpty() && viewModel.phoneNumber.value.isNotEmpty() &&
                            (viewModel.paymentMethod.value == "Cash" || (viewModel.cardNumber.value.isNotEmpty() && viewModel.expiryDate.value.isNotEmpty() && viewModel.cvv.value.isNotEmpty()))

                Button(
                    onClick = {
                        if (allFieldsFilled) viewModel.placeOrder()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = allFieldsFilled
                ) {
                    Text("Buy Now")
                }

                if (viewModel.orderPlaced.value && allFieldsFilled) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
