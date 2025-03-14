package com.example.kaferest.presentation.entrance.register.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.kaferest.R
import com.example.kaferest.presentation.components.ButtonContent
import com.example.kaferest.presentation.components.CustomButton
import com.example.kaferest.presentation.components.CustomIconTextField
import com.example.kaferest.presentation.components.PasswordValidator
import com.example.kaferest.presentation.auth.menu.register.viewmodel.RegisterViewModel
import com.example.kaferest.presentation.navigation.Screen
import com.example.kaferest.ui.theme.Typography
import com.example.kaferest.presentation.entrance.register.viewmodel.RegisterScreenEvent
import com.example.kaferest.presentation.components.MenuBackButton
import androidx.compose.runtime.getValue
import com.example.kaferest.util.ValidationUtils

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val state = viewModel.uiState.value

    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val isFormValid by remember {
        derivedStateOf {
            ValidationUtils.validateRegistrationForm(
                name = nameState.value,
                email = emailState.value,
                password = passwordState.value
            )
        }
    }

    LaunchedEffect(state.isMailVerified) {
        if (state.isMailVerified) {
            println(nameState.value+ emailState.value+ passwordState.value)
            viewModel.onEvent(
                RegisterScreenEvent.SendVerificationMail(
                    userName = nameState.value,
                    userMail = emailState.value,
                    userPassword = passwordState.value
                )
            )
            navController.navigate(Screen.EmailVerificationScreen.route)
            viewModel.resetNavigation()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold { paddingValue ->
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(paddingValue)
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    //Go back to the LoginScreen
                    MenuBackButton(onClick = {navController.popBackStack(
                        Screen.LoginScreen.route,
                        inclusive = false
                    )})

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = stringResource(R.string.create_your_account),
                        style = Typography.displayMedium,
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Card(
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.sign_up),
                                style = Typography.displayMedium,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            CustomIconTextField(
                                stringState = nameState,
                                text = stringResource(R.string.name),
                                style = Typography.titleMedium,
                                textType = KeyboardType.Text,
                                leadingIcon = Icons.Default.Person,
                                leadIconDesc = stringResource(R.string.person_icon),
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            CustomIconTextField(
                                stringState = emailState,
                                text = stringResource(R.string.email),
                                style = Typography.titleMedium,
                                textType = KeyboardType.Email,
                                leadingIcon = Icons.Default.Email,
                                leadIconDesc = stringResource(R.string.email_icon),
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            if (state.emailExistError) {
                                Text(
                                    text = stringResource(R.string.this_email_is_already_registered),
                                    color = Color.Red,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            CustomIconTextField(
                                stringState = passwordState,
                                text = stringResource(R.string.password),
                                style = Typography.titleMedium,
                                textType = KeyboardType.Password,
                                leadingIcon = Icons.Default.Lock,
                                leadIconDesc = stringResource(R.string.lock_icon),
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            PasswordValidator(passwordState)

                            Spacer(modifier = Modifier.height(35.dp))

                            //Sign Up button
                            CustomButton(
                                onClick = {
                                    viewModel.onEvent(
                                        RegisterScreenEvent.CheckEmailExists(
                                            email = emailState.value
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                isOutlined = false,
                                enabled = isFormValid,
                                buttonContent = ButtonContent.IconContent(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = stringResource(R.string.sign_up_icon)
                                ),
                                text = stringResource(R.string.sign_up),
                                style = Typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            //Go back to login screen
                            CustomButton(
                                onClick = {
                                    navController.popBackStack(Screen.IntroScreen.route, inclusive = false)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                isOutlined = true,
                                buttonContent = ButtonContent.IconContent(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = stringResource(R.string.sign_in_icon)
                                ),
                                text = stringResource(R.string.login),
                                style = Typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                        }
                    }
                }
            }

            if (state.isLoading) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black.copy(alpha = 0.4f),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}
