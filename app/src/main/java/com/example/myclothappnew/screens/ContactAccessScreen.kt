package com.example.myclothappnew.ui

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext

@Composable
fun ContactAccessScreen() {
    val context = LocalContext.current
    var contacts by remember { mutableStateOf<List<String>>(emptyList()) }
    var permissionGranted by remember { mutableStateOf(false) }

    // Permission Request Launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
        if (isGranted) {
            contacts = getContacts(context)
        }
    }

    // Check Permission Status
    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            permissionGranted = true
            contacts = getContacts(context)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tap below to load contacts:", style = MaterialTheme.typography.bodyLarge)

        if (!permissionGranted) {
            Button(onClick = { requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) }) {
                Text("Grant Permission")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(contacts) { contact ->
                    Text(text = contact, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

// Function to fetch contacts
fun getContacts(context: Context): List<String> {
    val contactList = mutableListOf<String>()
    val contentResolver = context.contentResolver
    val cursor: Cursor? = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null, null, null
    )

    cursor?.use {
        val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (it.moveToNext()) {
            val name = it.getString(nameIndex)
            val number = it.getString(numberIndex)
            contactList.add("$name: $number")
        }
    }
    return contactList
}
