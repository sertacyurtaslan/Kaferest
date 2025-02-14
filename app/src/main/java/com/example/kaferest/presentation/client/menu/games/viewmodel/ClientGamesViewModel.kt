package com.example.kaferest.presentation.client.menu.games.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.data.preferences.PreferenceManager
import com.example.kaferest.domain.repository.KaferestRepository
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
import kotlin.random.Random

@HiltViewModel
class ClientGamesViewModel @Inject constructor(
    private val kaferestRepository: KaferestRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _state = MutableStateFlow(ClientGamesState())
    val state: StateFlow<ClientGamesState> = _state.asStateFlow()

    private var updateTimeJob: Job? = null

    init {
        //checkGameAvailability()
        //startTimeUpdates()
    }

    /*
    @SuppressLint("NewApi")
    private fun checkGameAvailability() {
        val now = LocalDateTime.now()
        
        // Check Spin Game
        val spinLastPlayed = preferenceManager.getSpinGameLastPlayed()
        if (spinLastPlayed > 0) {
            val lastPlayedDateTime = LocalDateTime.ofEpochSecond(spinLastPlayed, 0, ZoneOffset.UTC)
            val weeksSinceLastPlayed = ChronoUnit.WEEKS.between(lastPlayedDateTime, now)
            _state.value = _state.value.copy(
                canPlaySpinGame = weeksSinceLastPlayed >= 1,
                spinGameLastPlayed = spinLastPlayed
            )
        }

        // Check Scratch Game
        val scratchLastPlayed = preferenceManager.getScratchGameLastPlayed()
        if (scratchLastPlayed > 0) {
            val lastPlayedDateTime = LocalDateTime.ofEpochSecond(scratchLastPlayed, 0, ZoneOffset.UTC)
            val weeksSinceLastPlayed = ChronoUnit.WEEKS.between(lastPlayedDateTime, now)
            _state.value = _state.value.copy(
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

    @SuppressLint("NewApi")
    private fun updateRemainingTime() {
        val now = LocalDateTime.now()

        // Update Spin Game time
        if (!_state.value.canPlaySpinGame) {
            val lastPlayed = LocalDateTime.ofEpochSecond(
                _state.value.spinGameLastPlayed,
                0,
                ZoneOffset.UTC
            )
            val nextAvailable = lastPlayed.plusWeeks(1)
            val days = ChronoUnit.DAYS.between(now, nextAvailable)
            val hours = ChronoUnit.HOURS.between(now, nextAvailable) % 24
            _state.value = _state.value.copy(
                spinGameRemainingTime = "${days}d ${hours}h"
            )
        }

        // Update Scratch Game time
        if (!_state.value.canPlayScratchGame) {
            val lastPlayed = LocalDateTime.ofEpochSecond(
                _state.value.scratchGameLastPlayed,
                0,
                ZoneOffset.UTC
            )
            val nextAvailable = lastPlayed.plusWeeks(1)
            val days = ChronoUnit.DAYS.between(now, nextAvailable)
            val hours = ChronoUnit.HOURS.between(now, nextAvailable) % 24
            _state.value = _state.value.copy(
                scratchGameRemainingTime = "${days}d ${hours}h"
            )
        }
    }

    @SuppressLint("NewApi")
    fun spin() {
        if (!_state.value.canPlaySpinGame || _state.value.isSpinning) return

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isSpinning = true,
                    canSpin = false
                )

                // Random number of full rotations (3-5) plus random segment
                val fullRotations = Random.nextInt(3, 6) * 360
                val additionalAngle = Random.nextFloat() * 360
                val targetRotation = fullRotations + additionalAngle

                // Animate the spin
                val steps = 50
                val baseDelay = 3000L / steps // Total animation time: 3 seconds

                for (i in 0..steps) {
                    val progress = i.toFloat() / steps
                    val currentRotation = targetRotation * progress
                    _state.value = _state.value.copy(currentRotation = currentRotation)

                    // Exponential easing for more realistic spin
                    val delayMultiplier = 1 + (progress * progress)
                    delay((baseDelay * delayMultiplier).toLong())
                }

                // Calculate result based on final rotation
                val finalAngle = (targetRotation % 360)
                val segmentSize = 360f / _state.value.spinPoints.size
                val segment = (finalAngle / segmentSize).toInt()
                val prize = _state.value.spinPoints[segment]

                // Record the result
                val now = LocalDateTime.now()
                val gameResult = GameResult(
                    coinsWon = prize,
                    isWin = true,
                    lastPlayedTimestamp = now.toEpochSecond(ZoneOffset.UTC)
                )
                gameRepository.recordSpinGameResult(gameResult)

                // Update state
                preferenceManager.saveSpinGameLastPlayed(now.toEpochSecond(ZoneOffset.UTC))
                _state.value = _state.value.copy(
                    isSpinning = false,
                    spinResult = prize,
                    canPlaySpinGame = false,
                    spinGameLastPlayed = now.toEpochSecond(ZoneOffset.UTC)
                )

                // Refresh data
                loadUserCoins()
                loadGameStats()
                checkGameAvailability()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Error during spin game",
                    isSpinning = false,
                    canSpin = true
                )
            }
        }
    }
    @SuppressLint("NewApi")
    fun scratch() {
        if (!_state.value.canPlayScratchGame) return

        viewModelScope.launch {
            try {
                // Generate random prize (50-500 coins)
                val prize = Random.nextInt(50, 501)
                val now = LocalDateTime.now()


                // Update state
                preferenceManager.saveScratchGameLastPlayed(now.toEpochSecond(ZoneOffset.UTC))
                _state.value = _state.value.copy(
                    canPlayScratchGame = false,
                )

                // Refresh data
                //loadUserCoins()
                checkGameAvailability()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Error during scratch game"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }

    override fun onCleared() {
        super.onCleared()
        updateTimeJob?.cancel()
    }

     */
} 