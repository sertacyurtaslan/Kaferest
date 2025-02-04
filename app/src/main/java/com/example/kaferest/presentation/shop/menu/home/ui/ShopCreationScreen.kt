package com.example.kaferest.presentation.shop.menu.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kaferest.R
import com.example.kaferest.presentation.shop.menu.home.viewmodel.ShopCreationEvent
import com.example.kaferest.presentation.shop.menu.home.viewmodel.ShopCreationViewModel
import com.example.kaferest.presentation.components.MenuBackButton
import com.example.kaferest.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopCreationScreen(
    navController: NavController,
    viewModel: ShopCreationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    var currentStep by remember { mutableStateOf(0) }
    
    val steps = listOf(
        stringResource(R.string.enter_shop_name),
        stringResource(R.string.select_location),
        stringResource(R.string.add_shop_photos),
        stringResource(R.string.create_categories),
        stringResource(R.string.create_products)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_shop)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Stepper
            LinearProgressIndicator(
                progress = (currentStep + 1).toFloat() / steps.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            
            Text(
                text = steps[currentStep],
                style = Typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Content based on current step
            when (currentStep) {
                0 -> BasicInfoStep(
                    onNext = { newShopName ->
                        //Update the shop name as user's shop name
                        viewModel.onEvent(
                            ShopCreationEvent.UpdateShopName(
                                newShopName
                            )
                        )
                        currentStep++
                    }
                )
                1 -> LocationStep(
                    onLocationPermissionGranted = { /*viewModel.requestLocation()*/ },
                    onNext = { newShopAddress ->
                        viewModel.onEvent(
                            ShopCreationEvent.UpdateShopAdress(
                                newShopAddress
                            )
                        )
                        currentStep++ },
                    onBack = { currentStep-- }
                )

                2 -> PhotoStep(
                    onNext = { photos ->
                        viewModel.onEvent(
                            ShopCreationEvent.UpdateShopPhotos(photos)
                        )
                        currentStep++
                    },
                    onBack = { currentStep-- }
                )

                3 -> CategoriesStep(
                    categories = state.categories,
                    onCategoryAdded = { viewModel.addCategory(it) },
                    onCategoryRemoved = { viewModel.removeCategory(it) },
                    onNext = { currentStep++ },
                    onBack = { currentStep-- }
                )

                4 -> ProductsStep(
                    products = state.products,
                    categories = state.categories,
                    onProductAdded = { viewModel.addProduct(it) },
                    onProductRemoved = { viewModel.removeProduct(it) },
                    onFinish = { viewModel.createShop() },
                    onBack = { currentStep-- }
                )
            }
        }
    }

    // Show loading indicator
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    // Show error dialog
    if (state.error.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(state.error) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            }
        )
    }
}








