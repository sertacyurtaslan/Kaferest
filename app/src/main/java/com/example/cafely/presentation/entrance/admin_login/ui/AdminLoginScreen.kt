package com.example.cafely.presentation.entrance.admin_login.ui

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cafely.R
import com.example.cafely.presentation.components.ButtonContent
import com.example.cafely.presentation.components.CustomButton
import com.example.cafely.presentation.components.CustomIconTextField
import com.example.cafely.presentation.navigation.Screen
import com.example.cafely.ui.theme.Coffee
import com.example.cafely.ui.theme.CoffeeBlack
import com.example.cafely.ui.theme.CoffeeGradient
import com.example.cafely.ui.theme.Typography
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginScreenEvent
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginViewModel

@Composable
fun AdminLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val state = viewModel.uiState.value

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
                        contentDescription = stringResource(id = R.string.back),
                        tint = CoffeeBlack
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.ready_to_dive_into_your_shop_let_s_get_started),
                    style = Typography.displayMedium,
                    color = CoffeeBlack
                )

                Spacer(modifier = Modifier.height(100.dp))

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
                            text = stringResource(R.string.admin_login),
                            style = Typography.displayMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        CustomIconTextField(
                            stringState = emailState,
                            text = stringResource(R.string.email),
                            style = Typography.titleMedium,
                            textType = KeyboardType.Email,
                            leadingIcon = Icons.Default.Email,
                            leadIconDesc = stringResource(R.string.email_icon),
                            color = CoffeeBlack
                        )

                        Spacer(modifier = Modifier.height(10.dp))


                        CustomIconTextField(
                            stringState = passwordState,
                            text = stringResource(R.string.password),
                            style = Typography.titleMedium,
                            textType = KeyboardType.Password,
                            leadingIcon = Icons.Default.Lock,
                            leadIconDesc = stringResource(R.string.password_icon),
                            color = CoffeeBlack
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        if (state.matchError != null) {
                            Text(
                                text = state.matchError,
                                color = Color.Red,
                                style = Typography.titleMedium,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(40.dp))

                        CustomButton(
                            onClick = {
                                viewModel.onEvent(LoginScreenEvent.LoginUser(emailState.value, passwordState.value))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isOutlined = false,
                            buttonContent = ButtonContent.IconContent(
                                imageVector = Icons.Default.Home,
                                contentDescription = stringResource(R.string.sign_in_icon)
                            ),
                            text = stringResource(R.string.sign_in),
                            style = Typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                    }
                }
            }
        }
    }
}
