package com.example.kaferest.data.firebase

import com.example.kaferest.domain.model.User
import com.example.kaferest.domain.repository.CafelyRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CafelyRepositoryImpl(
    private val firestore: FirebaseFirestore
): CafelyRepository {

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
}