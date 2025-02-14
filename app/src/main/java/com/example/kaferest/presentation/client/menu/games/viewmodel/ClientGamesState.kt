package com.example.kaferest.presentation.client.menu.games.viewmodel

import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlin.collections.listOf

data class ClientGamesState(
    // Common Game State
    val coins: Int = 0,
    val canPlaySpinGame: Boolean = true,
    val canPlayScratchGame: Boolean = true,
    // Time Tracking
    val spinGameRemainingTime: String = "",
    val scratchGameRemainingTime: String = "",

    // Spin Game State
    val isSpinning: Boolean = false,
    val canSpin: Boolean = true,
    val currentRotation: Float = 0f,
    val spinResult: Int = 0,
    val spinPoints: List<Int> = listOf(100, 200, 300, 400, 500, 600, 700, 800),
    
    val isLoading: Boolean = false,
    val error: String = ""
) 