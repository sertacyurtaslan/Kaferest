package com.example.kaferest.presentation.entrance.admin_login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kaferest.R
import com.example.kaferest.presentation.components.ButtonContent
import com.example.kaferest.presentation.components.CustomButton
import com.example.kaferest.presentation.components.CustomIconTextField
import com.example.kaferest.presentation.components.MenuBackButton
/*
import com.example.cafely.ui.theme.Coffee
import com.example.cafely.ui.theme.CoffeeBlack
import com.example.cafely.ui.theme.CoffeeGradient
 */
import com.example.kaferest.ui.theme.Typography
import com.example.kaferest.presentation.entrance.login.viewmodel.LoginViewModel

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
                    .padding(paddingValue)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                MenuBackButton(onClick = {navController.navigateUp()})

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.ready_to_dive_into_your_shop),
                    style = Typography.displayMedium,
                    //color = CoffeeBlack
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.let_get_started),
                    style = Typography.titleMedium,
                    //color = CoffeeBlack
                )

                Spacer(modifier = Modifier.height(100.dp))

                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    /*
                    colors = CardDefaults.cardColors(
                        containerColor = Coffee
                    )
                     */
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
                            //color = CoffeeBlack
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CustomIconTextField(
                            stringState = passwordState,
                            text = stringResource(R.string.password),
                            style = Typography.titleMedium,
                            textType = KeyboardType.Password,
                            leadingIcon = Icons.Default.Lock,
                            leadIconDesc = stringResource(R.string.password_icon),
                            //color = CoffeeBlack
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
                                //viewModel.onEvent(HomeScreenEvent.LoginUser(emailState.value, passwordState.value))
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
