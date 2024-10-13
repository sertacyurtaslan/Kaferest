@file:Suppress("DEPRECATION")

package com.example.financecompose.presentation.entrance.preferences.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*

@Composable
fun SavedAnimation(
    modifier: Modifier = Modifier,
    animationFile: Int,
    onAnimationFinish: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationFile))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1.0f
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )

    LaunchedEffect(progress) {
        if (progress == 1.0f) {
            onAnimationFinish()
        }
    }
}
