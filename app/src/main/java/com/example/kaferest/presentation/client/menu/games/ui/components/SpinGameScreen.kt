package com.example.kaferest.presentation.client.menu.games.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kaferest.presentation.client.nav.viewmodel.ClientMainState
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
/*
@Composable
fun SpinGameScreen(
    state: ClientMainState,
    onSpin: () -> Unit,
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
            text = "Spin & Win",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        if (!state.canPlaySpinGame) {
            Text(
                text = "Next spin available in: ${state.spinGameRemainingTime}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }

        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            SpinWheel(
                points = state.spinPoints,
                currentRotation = state.currentRotation
            )
        }

        Button(
            onClick = onSpin,
            enabled = state.canPlaySpinGame && !state.isSpinning
        ) {
            Text(text = if (state.isSpinning) "Spinning..." else "Spin")
        }

        if (state.spinResult > 0) {
            Text(
                text = "You won ${state.spinResult} coins!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stats
        state.spinGameStats?.let { stats ->
            Text(
                text = "Total wins: ${stats.totalWins}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun SpinWheel(
    points: List<Int>,
    currentRotation: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val rotation by animateFloatAsState(
        targetValue = currentRotation,
        animationSpec = tween(
            durationMillis = 3000,
            easing = LinearEasing
        ),
        label = "spin_wheel_rotation"
    )

    Canvas(
        modifier = modifier
            .size(300.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.width.coerceAtMost(size.height) / 2
        val segmentAngle = 360f / points.size

        rotate(rotation) {
            points.forEachIndexed { index, points ->
                val startAngle = index * segmentAngle
                val color = when (index % 4) {
                    0 -> Color(0xFF4CAF50) // Green
                    1 -> Color(0xFF2196F3) // Blue
                    2 -> Color(0xFFFFC107) // Yellow
                    else -> Color(0xFFE91E63) // Pink
                }

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = segmentAngle,
                    useCenter = true,
                    size = Size(size.width, size.height)
                )

                // Draw points text
                val textRadius = radius * 0.7f
                val angle = (startAngle + segmentAngle / 2) * (PI / 180f)
                val textLayoutResult = textMeasurer.measure(
                    text = points.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )

                val textOffset = Offset(
                    (center.x + cos(angle).toFloat() * textRadius - textLayoutResult.size.width / 2),
                    (center.y + sin(angle).toFloat() * textRadius - textLayoutResult.size.height / 2)
                )

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = textOffset
                )
            }
        }

        // Draw center point
        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = center
        )
    }
}
*/