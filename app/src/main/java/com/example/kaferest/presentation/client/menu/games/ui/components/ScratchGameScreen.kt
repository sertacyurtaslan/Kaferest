package com.example.kaferest.presentation.client.menu.games.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kaferest.presentation.client.nav.viewmodel.ClientMainState
/*
@Composable
fun ScratchGameScreen(
    state: ClientMainState,
    onScratch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Scratch & Win",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        if (!state.canPlayScratchGame) {
            Text(
                text = "Next scratch available in: ${state.scratchGameRemainingTime}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }

        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            ScratchCard(
                enabled = state.canPlayScratchGame,
                onScratchComplete = onScratch
            )
        }

        // Stats
        state.scratchGameStats?.let { stats ->
            Text(
                text = "Total wins: ${stats.totalWins}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ScratchCard(
    enabled: Boolean,
    onScratchComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var scratchedPoints by remember { mutableStateOf(mutableListOf<Offset>()) }
    var scratchedArea by remember { mutableStateOf(0f) }
    val totalArea = 300 * 300 // Total area of the card
    val scratchThreshold = 0.6f // 60% of the card needs to be scratched

    Canvas(
        modifier = modifier
            .size(300.dp)
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    scratchedPoints.add(change.position)
                    // Calculate scratched area (simplified)
                    scratchedArea = scratchedPoints.size * 100f / totalArea
                    if (scratchedArea >= scratchThreshold) {
                        onScratchComplete()
                    }
                }
            }
    ) {
        // Draw prize text
        drawText(
            textLayoutResult = androidx.compose.ui.text.rememberTextMeasurer().measure(
                text = "Prize!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            ),
            topLeft = Offset(size.width / 3, size.height / 2)
        )

        // Draw scratch layer
        drawRect(
            color = Color.Gray,
            size = size
        )

        // Draw scratched areas
        scratchedPoints.forEach { point ->
            drawCircle(
                color = Color.Transparent,
                radius = 30f,
                center = point,
                blendMode = BlendMode.Clear
            )
        }

        // Draw grid pattern
        val gridSize = 20f
        for (x in 0..size.width.toInt() step gridSize.toInt()) {
            drawLine(
                color = Color.White,
                start = Offset(x.toFloat(), 0f),
                end = Offset(x.toFloat(), size.height),
                strokeWidth = 1f
            )
        }
        for (y in 0..size.height.toInt() step gridSize.toInt()) {
            drawLine(
                color = Color.White,
                start = Offset(0f, y.toFloat()),
                end = Offset(size.width, y.toFloat()),
                strokeWidth = 1f
            )
        }
    }
} */