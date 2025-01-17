package com.example.kaferest.domain.repository

import com.example.kaferest.domain.model.User

interface CafelyRepository {
    suspend fun createUser(userData: User): User
    suspend fun getUser(userId: String): User?
}