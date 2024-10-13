package com.example.financecompose.presentation.entrance.preferences.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cafely.R
import com.example.financecompose.presentation.entrance.preferences.viewmodel.PreferencesViewModel

@Composable
fun PreferencesScreen(
    navController: NavController,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    val balanceState = remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("") }
    var showAnimation by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValue ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(paddingValue)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                /*
                Text(
                    text = stringResource(R.string.select_your_preferences),
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    //fontFamily = FontFamily(Font(R.font.kanit)),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.preferences),
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                                //fontFamily = FontFamily(Font(R.font.kanit)),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            CurrencyDropdownMenu(
                                selectedCurrency = "USD".also { selectedCurrency = it },
                                onCurrencySelected = { selectedCurrency = it }
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            CustomIconTextField(
                                stringState = balanceState,
                                text = stringResource(R.string.balance),
                                fontFamily = FontFamily(Font(R.font.kanit)),
                                textType = KeyboardType.Number,
                                textSize = 15,
                                leadingIcon = Icons.Default.Edit,
                                leadIconDesc = stringResource(R.string.balance_icon)
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            if (showAnimation) {
                                SavedAnimation(
                                    animationFile = R.raw.saved_animation, // replace with your actual animation file
                                    onAnimationFinish = {
                                        showAnimation = false
                                        navController.navigate("home_screen")
                                    },
                                    modifier = Modifier.fillMaxWidth()

                                )
                            } else {
                                Button(
                                    onClick = {
                                        viewModel.saveUserPreferences(balanceState.value, selectedCurrency)
                                        showAnimation = true
                                        keyboardController?.hide() // Hide the keyboard
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = stringResource(R.string.t_continue),
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.kanit)),
                                        color = Color.Black
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = stringResource(R.string.already_have_an_account_log_in),
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.eina)),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                 */
            }
        }
    }
}

