package com.example.cafely.presentation.entrance.register.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cafely.R
import com.example.cafely.presentation.components.ButtonContent
import com.example.cafely.presentation.components.CustomButton
import com.example.cafely.presentation.components.CustomIconTextField
import com.example.cafely.presentation.components.PasswordValidator
import com.example.cafely.presentation.entrance.register.viewmodel.RegisterViewModel
import com.example.cafely.presentation.navigation.Screen
import com.example.cafely.ui.theme.Coffee
import com.example.cafely.ui.theme.CoffeeBlack
import com.example.cafely.ui.theme.CoffeeGradient
import com.example.cafely.ui.theme.Typography
import com.example.financecompose.presentation.entrance.register.viewmodel.RegisterScreenEvent

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val state = viewModel.uiState.value

    /*
    if (state.isRegistered) {
        navController.navigate(Screen.PreferencesScreen.route)
    }

     */

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValue ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(
                        CoffeeGradient
                    )
                    .padding(paddingValue)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                IconButton(onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = stringResource(R.string.back),
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.create_your_account),
                    style = Typography.displayMedium,
                    color = CoffeeBlack
                )

                Spacer(modifier = Modifier.height(50.dp))

                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = Coffee
                    )
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
                            color = CoffeeBlack
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CustomIconTextField(
                            stringState = emailState,
                            text = stringResource(R.string.email),
                            style = Typography.titleMedium,
                            textType = KeyboardType.Email,
                            leadingIcon = Icons.Default.Email,
                            leadIconDesc = stringResource(R.string.email_icon),
                            color = CoffeeBlack
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        if (state.emailError != null) {
                            Text(
                                text = state.emailError,
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
                            color = CoffeeBlack
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        PasswordValidator(passwordState)

                        Spacer(modifier = Modifier.height(35.dp))

                        CustomButton(
                            onClick = {
                                viewModel.onEvent(
                                    RegisterScreenEvent.RegisterUser(
                                        nameState.value,
                                        emailState.value,
                                        passwordState.value
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isOutlined = false,
                            buttonContent = ButtonContent.IconContent(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(R.string.sign_up_icon)
                            ),
                            text = stringResource(R.string.sign_up),
                            style = Typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        CustomButton(
                            onClick = {
                                navController.navigate(Screen.LoginScreen.route)
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
    }
}
