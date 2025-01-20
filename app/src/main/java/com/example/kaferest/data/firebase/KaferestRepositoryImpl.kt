package com.example.kaferest.data.firebase

import com.example.kaferest.domain.model.User
import com.example.kaferest.domain.repository.KaferestRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class KaferestRepositoryImpl(
    private val firestore: FirebaseFirestore
): KaferestRepository {

    override suspend fun createUser(userData: User): User {
        val userDocument = userData.userId?.let { firestore.collection("users").document(it) }
        userDocument?.set(userData)?.await()
        return userData
    }

    override suspend fun getUser(userId: String): User? {
        val document = firestore.collection("users")
            .document(userId)
            .get()
            .await()
        return if (document.exists()) {
            document.toObject(User::class.java)
        } else {
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return try {
            val document = firestore.collection("users")
                .whereEqualTo("userEmail", email)
                .get()
                .await()
            if (!document.isEmpty) {
                document.documents[0].toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error getting user by email: ${e.message}")
            null
        }
    }

    override suspend fun updateUser(user: User): User {
        return try {
            user.userId?.let {
                firestore.collection("users")
                    .document(it)
                    .set(user)
                    .await()
            }
            user
        } catch (e: Exception) {
            println("Error updating user: ${e.message}")
            throw e
        }
    }
}