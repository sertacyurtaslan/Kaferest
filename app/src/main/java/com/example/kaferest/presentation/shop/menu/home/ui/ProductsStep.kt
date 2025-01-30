package com.example.kaferest.presentation.shop.menu.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kaferest.R

@Composable
private fun ProductsStep(
    products: List<Product>,
    categories: List<String>,
    onProductAdded: (Product) -> Unit,
    onProductRemoved: (Product) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = { showAddDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.add_product))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Products list
        // TODO: Implement products list UI

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.back))
            }
            Button(
                onClick = onFinish,
                enabled = products.isNotEmpty()
            ) {
                Text(stringResource(R.string.finish))
            }
        }
    }

    if (showAddDialog) {
        AddProductDialog(
            categories = categories,
            onProductAdded = {
                onProductAdded(it)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }
}