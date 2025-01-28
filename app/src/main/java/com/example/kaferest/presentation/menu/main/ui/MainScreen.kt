package com.example.kaferest.presentation.menu.main.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaferest.R
import com.example.kaferest.presentation.menu.games.ui.GamesScreen
import com.example.kaferest.presentation.menu.home.ui.HomeScreen
import com.example.kaferest.presentation.menu.qr.ui.QrScreen
import com.example.kaferest.presentation.menu.settings.ui.SettingsScreen
import com.example.kaferest.presentation.menu.shops.ui.ShopsScreen
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "MainScreen"

@Composable
fun MainScreen(
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        currentUser?.let { user ->
            Log.d(TAG, "Current User Data:")
            Log.d(TAG, "UID: ${user.uid}")
            Log.d(TAG, "Display Name: ${user.displayName}")
            Log.d(TAG, "Email: ${user.email}")
            
            // Print provider data
            user.providerData.forEach { userInfo ->
                Log.d(TAG, "Provider: ${userInfo.providerId}")
            }

            // Get ID token
            user.getIdToken(false).addOnSuccessListener { result ->
                Log.d(TAG, "ID Token: ${result.token}")
            }
        } ?: Log.d(TAG, "No user is currently signed in")
    }

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("qr") {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .size(65.dp)
                    .offset(y = (65).dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.qr_code),
                    contentDescription = "QR",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") { 
                    HomeScreen(rootNavController) 
                }
                composable("shops") { 
                    ShopsScreen(rootNavController) 
                }
                composable("qr") {
                    QrScreen(rootNavController)
                }
                composable("games") { 
                    GamesScreen(rootNavController) 
                }
                composable("settings") { 
                    SettingsScreen(rootNavController) 
                }
            }
        }
    }
}