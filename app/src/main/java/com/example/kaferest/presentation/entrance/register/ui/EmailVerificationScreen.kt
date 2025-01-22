package com.example.kaferest.presentation.entrance.register.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kaferest.R
import com.example.kaferest.presentation.components.ButtonContent
import com.example.kaferest.presentation.components.CustomButton
import com.example.kaferest.presentation.components.CustomIconTextField
import com.example.kaferest.presentation.entrance.register.viewmodel.RegisterScreenEvent
import com.example.kaferest.presentation.entrance.register.viewmodel.RegisterViewModel
import com.example.kaferest.presentation.navigation.Screen
import com.example.kaferest.ui.theme.Typography

@Composable
fun EmailVerificationScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val state = viewModel.uiState.value
    val canResend by viewModel.canResend.collectAsState()
    val remainingSeconds by viewModel.remainingSeconds.collectAsState()

    val codeState = remember { mutableStateOf("") }

    LaunchedEffect(state.isRegistered) {

        if (state.isRegistered) {
            navController.navigate(Screen.HomeScreen.route)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValue ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.check_your_email),
                    style = Typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.we_sent_you_verification_code),
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(150.dp))

                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.verify_mail),
                            style = Typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        CustomIconTextField(
                            stringState = codeState,
                            text = stringResource(R.string.verification_code),
                            style = Typography.titleMedium,
                            textType = KeyboardType.Number,
                            leadingIcon = Icons.Default.Lock,
                            leadIconDesc = stringResource(R.string.verification_code),
                            characterLimit = 6
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        CustomButton(
                            onClick = {
                                viewModel.onEvent(
                                RegisterScreenEvent.VerifyCodeAndRegister(codeState.value)
                            )},
                            modifier = Modifier.fillMaxWidth(),
                            isOutlined = false,
                            buttonContent = ButtonContent.IconContent(
                                imageVector = Icons.Default.Email,
                                contentDescription = stringResource(R.string.verify_mail)
                            ),
                            text = stringResource(R.string.verify_mail),
                            style = Typography.titleMedium
                        )

                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Resend button
                        TextButton(
                            onClick = { viewModel.resendVerificationEmail() },
                            enabled = canResend,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (canResend) {
                                    stringResource(R.string.resend_code)
                                } else {
                                    stringResource(R.string.resend_code_in_seconds, remainingSeconds)
                                },
                                style = Typography.bodyMedium,
                                color = if (canResend) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

