package com.example.cafely.presentation.entrance.forgot_password.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cafely.R
import com.example.cafely.presentation.components.CustomIconTextField
import com.example.financecompose.presentation.entrance.forgot_password.ui.PasswordResetDialog
import com.example.cafely.presentation.entrance.forgot_password.viewmodel.ForgotPasswordScreenEvent
import com.example.cafely.presentation.entrance.forgot_password.viewmodel.ForgotPasswordViewModel
import com.example.cafely.presentation.navigation.Screen
import com.example.cafely.ui.theme.Typography

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {

    val emailState = remember { mutableStateOf("") }
    var emailError: Boolean by remember { mutableStateOf(false) }
    var showDialog: Boolean by remember { mutableStateOf(false) }

    val state = viewModel.uiState.value

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValue ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(
                        Color.Black
                    )
                    .padding(paddingValue)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                // Back button
                IconButton(onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = stringResource(id = R.string.back),
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Welcome back text
                Text(
                    text = stringResource(R.string.enter_the_email_adress_you_used_to_sign_in),
                    fontSize = 30.sp,
                    /*
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily(Font(R.font.kanit)),

                     */
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(100.dp))

                // Login form
                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Login title
                        Text(
                            text = stringResource(R.string.password_reset),
                            fontSize = 30.sp,
                            /*
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily(Font(R.font.kanit)),

                             */
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(20.dp))


                        CustomIconTextField(
                            stringState = emailState,
                            text = stringResource(id = R.string.email),
                            style = Typography.titleMedium,
                            textType = KeyboardType.Email,
                            leadingIcon = Icons.Default.Email,
                            leadIconDesc = stringResource(id = R.string.email_icon)
                        )
                        if (emailError) {
                            Text(
                                text = "*Invalid email format",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(35.dp))

                        // Send mail button
                        Button(
                            onClick = {
                                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailState.value).matches()) {
                                    viewModel.onEvent(ForgotPasswordScreenEvent.SendPasswordResetMail(emailState.value))
                                    showDialog = true
                                    emailError = false
                                } else {
                                    emailError = true
                                }
                            },
                            //colors = ButtonDefaults.buttonColors(Seapl),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.send_email))
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.we_ll_send_you_a_password_reset_mail),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Normal,
                            //fontFamily = FontFamily(Font(R.font.kanit)),
                            //color = TextColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

                if (showDialog) {
                    PasswordResetDialog(
                        onDismiss = { showDialog = false },
                        onReturnSignIn = {
                            navController.navigate(Screen.LoginScreen.route)
                        },
                        onResendEmail = {viewModel.onEvent(
                            ForgotPasswordScreenEvent
                            .SendPasswordResetMail(
                                emailState.value
                            ))
                        }
                    )
                }

            }
        }
    }
}
