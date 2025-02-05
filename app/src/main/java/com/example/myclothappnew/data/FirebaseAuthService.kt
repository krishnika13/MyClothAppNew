package com.example.myclothappnew.data


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class FirebaseAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Register User
    suspend fun registerUser(email: String, password: String): AuthResult? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result
        } catch (e: Exception) {
            null
        }
    }

    // Login User
    suspend fun loginUser(email: String, password: String): AuthResult? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result
        } catch (e: Exception) {
            null
        }
    }

    // Check if user is logged in
    fun isUserLoggedIn() = auth.currentUser != null

    // Log out user
    fun logoutUser() = auth.signOut()
}
