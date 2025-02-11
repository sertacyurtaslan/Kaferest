package com.example.kaferest.presentation.admin.menu.shop_creation.ui.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kaferest.R
import com.example.kaferest.domain.model.Category
import com.example.kaferest.domain.model.Product

@Composable
fun ProductsStep(
    categories: List<Category>,
    onProductAdded: (Product, String) -> Unit,
    onProductRemoved: (Product, String) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit,
    onCategoryRemoved: (String) -> Unit
) {
    var expandedCategory by remember { mutableStateOf<String?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    var showDeleteProductDialog by remember { mutableStateOf<Pair<Product, String>?>(null) }
    var showDeleteCategoryDialog by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isExpanded = expandedCategory == category.categoryName

                CategoryItem(
                    category = category.categoryName,
                    isExpanded = isExpanded,
                    productsCount = category.categoryProducts.size,
                    onExpandClick = {
                        expandedCategory = if (isExpanded) null else category.categoryName
                    },
                    onAddProductClick = {
                        selectedCategory = category.categoryName
                        showAddDialog = true
                    },
                    onDeleteCategoryClick = {
                        showDeleteCategoryDialog = category.categoryName
                    }
                ) {
                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (category.categoryProducts.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_products),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            } else {
                                category.categoryProducts.forEach { product ->
                                    ProductItem(
                                        product = product,
                                        onDeleteClick = { 
                                            showDeleteProductDialog = Pair(product, category.categoryName)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.back))
            }
            Button(
                onClick = onFinish,
                enabled = categories.any { it.categoryProducts.isNotEmpty() }
            ) {
                Text(stringResource(R.string.finish))
            }
        }
    }

    // Dialogs

    //Add Product
    if (showAddDialog) {
        AddProductDialog(
            selectedCategory = selectedCategory,
            onProductAdded = { product ->
                onProductAdded(product, selectedCategory)
                expandedCategory = selectedCategory
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    //Delete Product
    showDeleteProductDialog?.let { (product, categoryName) ->
        AlertDialog(
            onDismissRequest = { showDeleteProductDialog = null },
            title = { Text(stringResource(R.string.delete_product_title)) },
            text = { Text(stringResource(R.string.delete_product_confirmation, product.productName)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onProductRemoved(product, categoryName)
                        showDeleteProductDialog = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteProductDialog = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    //DeleteCategory
    showDeleteCategoryDialog?.let { category ->
        AlertDialog(
            onDismissRequest = { showDeleteCategoryDialog = null },
            title = { Text(stringResource(R.string.delete_category_title)) },
            text = { Text(stringResource(R.string.delete_category_confirmation, category)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCategoryRemoved(category)
                        showDeleteCategoryDialog = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteCategoryDialog = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}



