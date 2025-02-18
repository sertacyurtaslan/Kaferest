package com.example.kaferest.presentation.shop.menu.shop_creation.ui.categories

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kaferest.R
import com.example.kaferest.data.enums.PredefinedCategories
import com.example.kaferest.domain.model.Category

@Composable
fun CategoriesStep(
    categories: List<Category>,
    onCategoryAdded: (Category) -> Unit,
    onCategoryRemoved: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var customCategories by remember { mutableStateOf<List<Category>>(emptyList()) }

    // Get predefined categories
    val predefinedCategories = remember {
        PredefinedCategories.getEntries()
    }.map { category ->
        Category(
            categoryName = stringResource(category.titleRes),
            categoryEmoji = category.emoji
        )
    }

    // Combine predefined categories with custom categories
    val allCategories = remember(categories, customCategories) {
        predefinedCategories + customCategories
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(allCategories) { category ->
                val isSelected = categories.any { it.categoryName == category.categoryName }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            if (isSelected) {
                                onCategoryRemoved(category.categoryName)
                            } else {
                                onCategoryAdded(category)
                            }
                        }
                        .border(
                            width = 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                           else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = category.categoryEmoji,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = category.categoryName,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                                   else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { showAddDialog = true }
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.add_custom),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_custom_category_desc),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
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
                onClick = onNext,
                enabled = categories.isNotEmpty()
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }

    if (showAddDialog) {
        AddCustomCategoryDialog(
            onDismiss = { showAddDialog = false },
            onCategoryAdded = { category ->
                customCategories = customCategories + category
                onCategoryAdded(category)
            }
        )
    }
}