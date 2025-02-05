package com.example.myclothappnew.screens

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import appBackgroundColor
import appTextColor
import buttonColor
import com.example.myclothappnew.data.ProfileDataStore
import com.example.myclothappnew.ui.component.BottomNavigationBar
import com.example.myclothappnew.util.FileHelper
import kotlinx.coroutines.launch
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun UserProfilePage(navController: NavHostController) {
    val context = LocalContext.current // Get the context from the current Composable
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val profileDataStore = remember { ProfileDataStore(context) }
    val coroutineScope = rememberCoroutineScope()

    // Load the saved image when the app starts
    LaunchedEffect(Unit) {
        profileDataStore.profileImagePath.collect { path ->
            if (path != null) {
                selectedImageBitmap = FileHelper.loadImageFromInternalStorage(path)
            }
        }
    }

    // Image Picker (Gallery)
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                // Save the image to internal storage
                val savedPath = FileHelper.saveImageToInternalStorage(context, uri)
                if (savedPath != null) {
                    profileDataStore.saveProfileImagePath(savedPath)
                    selectedImageBitmap = FileHelper.loadImageFromInternalStorage(savedPath)
                }
            }
        }
    }

    // Camera Access
    var cameraUri: Uri? by remember { mutableStateOf(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess: Boolean ->
        if (isSuccess && cameraUri != null) {
            coroutineScope.launch {
                // Save the image to internal storage
                val savedPath = FileHelper.saveImageToInternalStorage(context, cameraUri!!)
                if (savedPath != null) {
                    profileDataStore.saveProfileImagePath(savedPath)
                    selectedImageBitmap = FileHelper.loadImageFromInternalStorage(savedPath)
                }
            }
        }
    }

    // Create a Uri to save the photo taken from the camera
    fun createCameraUri(): Uri {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    Column(
        modifier = Modifier.fillMaxSize().background(appBackgroundColor()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Profile picture
                Box(
                    modifier = Modifier.size(150.dp).clip(CircleShape).background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageBitmap != null) {
                        Image(
                            bitmap = selectedImageBitmap!!.asImageBitmap(),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    } else {
                        Text(text = "No Image", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Email: user@gmail.com",
                    fontSize = 20.sp,
                    color = appTextColor(),
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Gallery Image Picker
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor()),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Add Profile Photo from Gallery")
                }

                Spacer(modifier = Modifier.height(8.dp))


                Button(
                    onClick = { /* Add logic for editing profile if needed */ },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor()),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Edit profile")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        // Clear profile image path and navigate to login page
                        coroutineScope.launch {
                            profileDataStore.clearProfileImagePath()
                        }
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor()),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Log Out")
                }
            }
        }

        BottomNavigationBar(
            currentScreen = "Profile",
            onNavigateToCart = { navController.navigate("cartPage") },
            onNavigateToProfile = { navController.navigate("profile") },
            onNavigateToHome = { navController.navigate("available_products") }
        )
    }
}
