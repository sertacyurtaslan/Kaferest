package com.example.kaferest.domain.repository

import com.example.kaferest.domain.model.User
import com.example.kaferest.domain.model.Shop

interface KaferestRepository {
    suspend fun createUser(userData: User): User
    suspend fun getUser(userId: String): User?
    suspend fun getShops(): List<Shop>
    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User): User
    suspend fun updateUserCoins(coins: Double)
}