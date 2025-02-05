package com.example.myclothappnew.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {
    var username = mutableStateOf("")
    var email = mutableStateOf("")
    var profilePictureUri = mutableStateOf<Uri?>(null)

    // Function to update user data
    fun setUserData(userName: String, userEmail: String, profileUri: Uri?) {
        username.value = userName
        email.value = userEmail
        profilePictureUri.value = profileUri
    }
}
