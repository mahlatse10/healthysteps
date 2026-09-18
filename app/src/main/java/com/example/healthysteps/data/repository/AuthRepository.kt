package com.example.healthysteps.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<String> {
        return try {
            val cleanName = name.trim()
            val cleanEmail = email.trim()

            if (cleanName.isBlank()) {
                return Result.failure(
                    Exception("Please enter your full name.")
                )
            }

            if (cleanEmail.isBlank()) {
                return Result.failure(
                    Exception("Please enter your email address.")
                )
            }

            if (password.isBlank()) {
                return Result.failure(
                    Exception("Please enter a password.")
                )
            }

            if (password.length < 6) {
                return Result.failure(
                    Exception("Password must be at least 6 characters.")
                )
            }

            val result = auth
                .createUserWithEmailAndPassword(
                    cleanEmail,
                    password
                )
                .await()

            val user = result.user
                ?: return Result.failure(
                    Exception("Registration failed. No user account was returned.")
                )

            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(cleanName)
                .build()

            user.updateProfile(profileUpdate).await()

            Result.success(user.uid)

        } catch (e: Exception) {
            Result.failure(
                Exception(getFriendlyErrorMessage(e))
            )
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<String> {
        return try {
            val cleanEmail = email.trim()

            if (cleanEmail.isBlank()) {
                return Result.failure(
                    Exception("Please enter your email address.")
                )
            }

            if (password.isBlank()) {
                return Result.failure(
                    Exception("Please enter your password.")
                )
            }

            val result = auth
                .signInWithEmailAndPassword(
                    cleanEmail,
                    password
                )
                .await()

            val user = result.user
                ?: return Result.failure(
                    Exception("Login failed. No user account was returned.")
                )

            Result.success(user.uid)

        } catch (e: Exception) {
            Result.failure(
                Exception(getFriendlyErrorMessage(e))
            )
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    fun getCurrentUserName(): String {
        return auth.currentUser?.displayName ?: ""
    }

    private fun getFriendlyErrorMessage(
        exception: Exception
    ): String {

        val message = exception.message?.lowercase() ?: ""

        return when {
            message.contains("password is invalid") ||
                    message.contains("invalid credential") ||
                    message.contains("invalid-credential") ->
                "Incorrect email or password."

            message.contains("email address is badly formatted") ||
                    message.contains("invalid-email") ->
                "Please enter a valid email address."

            message.contains("email address is already in use") ||
                    message.contains("email-already-in-use") ->
                "An account with this email already exists."

            message.contains("password should be at least") ||
                    message.contains("weak-password") ->
                "Password must be at least 6 characters."

            message.contains("network") ->
                "Network error. Please check your internet connection."

            message.contains("too many requests") ->
                "Too many attempts. Please try again later."

            else ->
                "Authentication failed. Please check your details and try again."
        }
    }
}