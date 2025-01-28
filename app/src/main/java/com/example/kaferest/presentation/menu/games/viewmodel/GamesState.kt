package com.example.kaferest.presentation.menu.games.viewmodel

data class GamesState(
    val coins: Int = 0,
    val canPlaySpinGame: Boolean = true,
    val canPlayScratchGame: Boolean = true,
    val spinGameLastPlayed: Long = 0L,
    val scratchGameLastPlayed: Long = 0L,
    val spinGameRemainingTime: String = "",
    val scratchGameRemainingTime: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
) 