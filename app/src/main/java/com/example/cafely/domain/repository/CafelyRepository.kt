package com.example.cafely.domain.repository

import com.example.cafely.domain.model.User

interface CafelyRepository {
    suspend fun createUser(userData: User): User
    suspend fun getUser(userId: String): User?
}