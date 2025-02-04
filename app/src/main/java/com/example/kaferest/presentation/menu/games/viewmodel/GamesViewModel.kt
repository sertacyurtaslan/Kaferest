package com.example.kaferest.presentation.menu.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.data.preferences.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _gamesState = MutableStateFlow(GamesState())
    val gamesState: StateFlow<GamesState> = _gamesState.asStateFlow()


    private var updateTimeJob: Job? = null

    init {
        loadUserCoins()
        checkGameAvailability()
        startTimeUpdates()
    }

    private fun loadUserCoins() {
        viewModelScope.launch {
            try {
                // TODO: Load coins from Firebase
                _gamesState.value = _gamesState.value.copy(
                    coins = 100 // Default coins for testing
                )
            } catch (e: Exception) {
                _gamesState.value = _gamesState.value.copy(
                    error = e.message ?: "Error loading coins"
                )
            }
        }
    }

    private fun checkGameAvailability() {
        val now = LocalDateTime.now()
        
        // Check Spin Game
        val spinLastPlayed = preferenceManager.getSpinGameLastPlayed()
        if (spinLastPlayed > 0) {
            val lastPlayedDateTime = LocalDateTime.ofEpochSecond(spinLastPlayed, 0, ZoneOffset.UTC)
            val weeksSinceLastPlayed = ChronoUnit.WEEKS.between(lastPlayedDateTime, now)
            _gamesState.value = _gamesState.value.copy(
                canPlaySpinGame = weeksSinceLastPlayed >= 1,
                spinGameLastPlayed = spinLastPlayed
            )
        }

        // Check Scratch Game
        val scratchLastPlayed = preferenceManager.getScratchGameLastPlayed()
        if (scratchLastPlayed > 0) {
            val lastPlayedDateTime = LocalDateTime.ofEpochSecond(scratchLastPlayed, 0, ZoneOffset.UTC)
            val weeksSinceLastPlayed = ChronoUnit.WEEKS.between(lastPlayedDateTime, now)
            _gamesState.value = _gamesState.value.copy(
                canPlayScratchGame = weeksSinceLastPlayed >= 1,
                scratchGameLastPlayed = scratchLastPlayed
            )
        }
    }

    private fun startTimeUpdates() {
        updateTimeJob?.cancel()
        updateTimeJob = viewModelScope.launch {
            while (true) {
                updateRemainingTime()
                delay(1000 * 60) // Update every minute
            }
        }
    }

    private fun updateRemainingTime() {
        val now = LocalDateTime.now()

        // Update Spin Game time
        if (!_gamesState.value.canPlaySpinGame) {
            val lastPlayed = LocalDateTime.ofEpochSecond(
                _gamesState.value.spinGameLastPlayed,
                0,
                ZoneOffset.UTC
            )
            val nextAvailable = lastPlayed.plusWeeks(1)
            val days = ChronoUnit.DAYS.between(now, nextAvailable)
            val hours = ChronoUnit.HOURS.between(now, nextAvailable) % 24
            _gamesState.value = _gamesState.value.copy(
                spinGameRemainingTime = "${days}d ${hours}h"
            )
        }

        // Update Scratch Game time
        if (!_gamesState.value.canPlayScratchGame) {
            val lastPlayed = LocalDateTime.ofEpochSecond(
                _gamesState.value.scratchGameLastPlayed,
                0,
                ZoneOffset.UTC
            )
            val nextAvailable = lastPlayed.plusWeeks(1)
            val days = ChronoUnit.DAYS.between(now, nextAvailable)
            val hours = ChronoUnit.HOURS.between(now, nextAvailable) % 24
            _gamesState.value = _gamesState.value.copy(
                scratchGameRemainingTime = "${days}d ${hours}h"
            )
        }
    }

    fun onSpinGameCompleted(coinsWon: Int) {
        viewModelScope.launch {
            val now = LocalDateTime.now()
            preferenceManager.saveSpinGameLastPlayed(now.toEpochSecond(ZoneOffset.UTC))
            // TODO: Update coins in Firebase
            _gamesState.value = _gamesState.value.copy(
                coins = _gamesState.value.coins + coinsWon,
                canPlaySpinGame = false,
                spinGameLastPlayed = now.toEpochSecond(ZoneOffset.UTC)
            )
            checkGameAvailability()
        }
    }

    fun onScratchGameCompleted(coinsWon: Int) {
        viewModelScope.launch {
            val now = LocalDateTime.now()
            preferenceManager.saveScratchGameLastPlayed(now.toEpochSecond(ZoneOffset.UTC))
            // TODO: Update coins in Firebase
            _gamesState.value = _gamesState.value.copy(
                coins = _gamesState.value.coins + coinsWon,
                canPlayScratchGame = false,
                scratchGameLastPlayed = now.toEpochSecond(ZoneOffset.UTC)
            )
            checkGameAvailability()
        }
    }

    override fun onCleared() {
        super.onCleared()
        updateTimeJob?.cancel()
    }
} 