package com.example.kaferest.presentation.entrance.intro.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kaferest.presentation.navigation.Screen
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.kaferest.R
import com.example.kaferest.presentation.entrance.intro.viewmodel.GoogleAuthUiClient
import com.example.kaferest.presentation.entrance.intro.viewmodel.GoogleSignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import com.example.kaferest.ui.theme.Typography
import androidx.compose.runtime.Composable
import com.example.kaferest.presentation.components.LanguageMenu
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.kaferest.MainActivity

@Composable
fun IntroScreen(
    navController: NavController,
    googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by googleSignInViewModel.state.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                googleSignInViewModel.viewModelScope.launch {
                    try {
                        println("GoogleSignIn: Processing successful sign-in result")
                        googleSignInViewModel.signInWithIntent(result.data ?: return@launch)
                    } catch (e: Exception) {
                        println("GoogleSignIn: Exception during sign in - ${e.message}")
                        e.printStackTrace()

                    }
                }
            } else {
                println("GoogleSignIn: Sign-in cancelled or failed - resultCode: ${result.resultCode}")
                println("GoogleSignIn: Error data: ${result.data?.extras}")

            }
        }
    )

    var showLanguageMenu by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(Screen.IntroScreen.route) { inclusive = true }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(it)
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Language Icon and Menu
                    Box {
                        IconButton(
                            onClick = { showLanguageMenu = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_language_24),
                                contentDescription = stringResource(R.string.language),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        
                        LanguageMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false },
                            onLanguageSelected = { language ->
                                // First set the menu state to false
                                showLanguageMenu = false
                                // Then change the locale with applyOnly = true to avoid immediate recreation
                                when (language) {
                                    "tr" -> (context as MainActivity).changeLocaleAndRecreate("tr", applyOnly = true)
                                    "en" -> (context as MainActivity).changeLocaleAndRecreate("en", applyOnly = true)
                                }
                                // Finally, recreate after a very short delay to ensure menu is closed
                                (context as MainActivity).window.decorView.postDelayed({
                                    (context).recreate()
                                }, 150)
                            }
                        )
                    }
                }

                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.intro_animation))
                LottieAnimation(
                    composition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp),
                    iterations = LottieConstants.IterateForever
                )

                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(bottom = 40.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.align(CenterHorizontally)
                        ) {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = Typography.displayMedium,
                                modifier = Modifier.align(CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = stringResource(R.string.where_every_sip_tells_a_story),
                                style = Typography.titleMedium,
                                modifier = Modifier.align(CenterHorizontally)
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Column(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    googleSignInViewModel.viewModelScope.launch {
                                        try {
                                            val signInIntentSender = googleSignInViewModel.signIn()
                                            if (signInIntentSender == null) {
                                                println("GoogleSignIn: Sign-in intent sender is null")
                                                return@launch
                                            }
                                            launcher.launch(
                                                IntentSenderRequest.Builder(signInIntentSender).build()
                                            )
                                        } catch (e: Exception) {
                                            println("GoogleSignIn: Error launching sign-in - ${e.message}")
                                            e.printStackTrace()

                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(CenterHorizontally),
                            )

                            {

                                Image(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = stringResource(R.string.google_icon),
                                    modifier = Modifier.width(30.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))

                                Text(
                                    text = stringResource(R.string.continue_with_google),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    style = Typography.displaySmall,
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedButton(
                                onClick = {
                                    navController.navigate(Screen.LoginScreen.route)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(CenterHorizontally),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = stringResource(R.string.email_icon),
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Add some spacing (optional)

                                Text(
                                    text = stringResource(R.string.continue_with_email),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    style = Typography.displaySmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
