package com.example.kaferest.presentation.client.menu.games.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaferest.presentation.client.menu.games.viewmodel.ClientGamesViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
/*
@Composable
fun SpinGameScreen(
    viewModel: ClientGamesViewModel = hiltViewModel()
) {
    //val state = viewModel.state.collectAsState()

    /*
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Spin & Win",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                SpinWheel(
                    rotation = state.currentRotation,
                    points = state.points
                )
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.result > 0 && !state.isSpinning) {
                    Text(
                        text = "You won ${state.result} points!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Button(
                    onClick = { viewModel.spin() },
                    enabled = state.canSpin && !state.isSpinning,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = if (state.isSpinning) "Spinning..." else "SPIN",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SpinWheel(
    rotation: Float,
    points: List<Int>,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFF1976D2), // Blue
        Color(0xFF388E3C), // Green
        Color(0xFFF57C00), // Orange
        Color(0xFFD32F2F), // Red
        Color(0xFF7B1FA2), // Purple
        Color(0xFF0097A7), // Cyan
        Color(0xFFC2185B), // Pink
        Color(0xFF00796B)  // Teal
    )
    
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.width.coerceAtMost(size.height) / 2
        val segmentAngle = 360f / points.size
        
        // Draw segments
        rotate(rotation) {
            points.forEachIndexed { index, point ->
                val startAngle = index * segmentAngle
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = segmentAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )
                
                // Draw points text
                rotate(startAngle + segmentAngle / 2) {
                    val textRadius = radius * 0.7f
                    drawContext.canvas.nativeCanvas.apply {
                        rotate(-rotation - (startAngle + segmentAngle / 2)) {
                            drawText(
                                point.toString(),
                                (center.x + cos(PI / 2).toFloat() * textRadius).toFloat(),
                                (center.y + sin(PI / 2).toFloat() * textRadius).toFloat(),
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textSize = 40f
                                    textAlign = android.graphics.Paint.Align.CENTER
                                }
                            )
                        }
                    }
                }
            }
        }
        
        // Draw center circle
        drawCircle(
            color = Color.DarkGray,
            radius = radius * 0.1f,
            center = center
        )
        
        // Draw arrow
        val arrowSize = radius * 0.15f
        drawPath(
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(center.x, center.y - radius)
                lineTo(center.x - arrowSize, center.y - radius + arrowSize * 1.5f)
                lineTo(center.x + arrowSize, center.y - radius + arrowSize * 1.5f)
                close()
            },
            color = Color.Blue
        )
    }

}
     */