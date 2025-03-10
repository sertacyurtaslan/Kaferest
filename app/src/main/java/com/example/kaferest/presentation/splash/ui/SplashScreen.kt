package com.example.kaferest.presentation.splash.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kaferest.R
import com.example.kaferest.presentation.navigation.Screen
import com.example.kaferest.presentation.splash.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        startAnimation = true
    }

    // Only navigate when initialization is complete
    LaunchedEffect(state.isUserInitialized, state.isAdminInitialized, state.isUserSignedIn, state.isAdminSignedIn) {
        println("user:${state.isUserSignedIn}")
        println("admin:${state.isAdminSignedIn}")

        if (state.isUserInitialized) {
            when (state.isUserSignedIn) {
                true -> {
                    navController.navigate(Screen.ClientMainScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
                false -> {
                    navController.navigate(Screen.IntroScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            }
        }
        if (state.isAdminInitialized) {
            when (state.isAdminSignedIn) {
                true -> {
                    navController.navigate(Screen.ShopMainScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
                false -> {
                    navController.navigate(Screen.IntroScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }

    // Show blank screen until initialization is complete
    if (!(state.isAdminInitialized || state.isUserInitialized)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        return
    }

    // Show splash screen content only after initialization
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(120.dp)
                                .alpha(alphaAnim.value)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(id = R.string.app_name_capitalized),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            ),
                            modifier = Modifier.alpha(alphaAnim.value)
                        )
                    }
                }
            }
        }
    }
} 