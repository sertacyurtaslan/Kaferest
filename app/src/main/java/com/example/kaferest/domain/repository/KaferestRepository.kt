package com.example.kaferest.domain.repository

import com.example.kaferest.domain.model.User

interface KaferestRepository {
    suspend fun createUser(userData: User): User
    suspend fun getUser(userId: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User): User
}