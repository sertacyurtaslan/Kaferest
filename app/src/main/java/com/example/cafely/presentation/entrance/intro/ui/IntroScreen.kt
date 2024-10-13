package com.example.cafely.presentation.entrance.intro.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cafely.presentation.navigation.Screen
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cafely.R
import com.example.cafely.presentation.entrance.intro.viewmodel.GoogleAuthUiClient
import com.example.cafely.presentation.entrance.intro.viewmodel.GoogleSignInViewModel
import com.example.cafely.ui.theme.Coffee
import com.example.cafely.ui.theme.CoffeeBlack
import com.example.cafely.ui.theme.CoffeeGradient
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import com.example.cafely.ui.theme.Typography

@Composable
fun IntroScreen(
    navController: NavController,
    viewModel: GoogleSignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.viewModelScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            } else {
                // Handle cancel or failure
                Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(key1 = state.isNewUser, key2 = state.isSignInSuccessful) {
        /*
        if (state.isNewUser) {
            navController.navigate(Screen.PreferencesScreen.route)
            viewModel.resetState()

        }*/ if (state.isSignInSuccessful) {
            navController.navigate(Screen.HomeScreen.route)
            //viewModel.resetState()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(CoffeeGradient)
                    .padding(it)
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        // Language Menu appears
                    },
                    modifier = Modifier.align(End)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_language_24),
                        contentDescription = stringResource(R.string.language),
                        tint = CoffeeBlack
                    )
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
                        .padding(bottom = 30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Coffee
                    )
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
                                    viewModel.viewModelScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(CenterHorizontally),
                                border = BorderStroke(1.dp, CoffeeBlack)
                            )
                            {
                                Image(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = stringResource(R.string.google_icon),
                                    modifier = Modifier.width(30.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.continue_with_google),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    style = Typography.displaySmall,
                                    color = CoffeeBlack
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
                                border = BorderStroke(1.dp, CoffeeBlack)

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = stringResource(R.string.email_icon),
                                    tint = CoffeeBlack
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Add some spacing (optional)

                                Text(
                                    text = stringResource(R.string.continue_with_email),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    style = Typography.displaySmall,
                                    color = CoffeeBlack
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
