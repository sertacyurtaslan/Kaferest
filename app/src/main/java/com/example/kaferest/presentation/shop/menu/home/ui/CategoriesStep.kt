package com.example.kaferest.presentation.shop.menu.home.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kaferest.R

data class PredefinedCategory(
    val name: String,
    val emoji: String
)


val commonEmojis = listOf(
    // Food & Drinks
    "☕", "🔥", "❄️", "🍰", "🎂", "🍪", "🍳", "🥪", "🥗", "🍛",
    "🍔", "🍝", "🍓", "🍹", "🥤", "🍵", "🧉", "🌱", "🧒", "🌟",
    "🍽️", "🥘", "🥙", "🌮", "🌯", "🥨", "🥖", "🧁", "🍦", "🍨",
    // Additional Food
    "🥐", "🥯", "🥖", "🥨", "🥞", "🧇", "🧀", "🥩", "🥓", "🍖",
    "🍗", "🥚", "🍜", "🍲", "🥣", "🥡", "🥠", "🍱", "🍘", "🍙",
    // Drinks & Desserts
    "🍺", "🍷", "🍸", "🍾", "🥂", "🧃", "🧊", "🍶", "🥛", "🍼",
    "🍮", "🍯", "🍬", "🍭", "🍫", "🍿", "🥜", "🌰", "🥮", "🍡",
    // Fruits & Vegetables
    "🍎", "🍐", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🫐", "🥝",
    "🍅", "🥑", "🥦", "🥬", "🥒", "🌶️", "🫑", "🧄", "🧅", "🥕"
)

@Composable
fun CategoriesStep(
    categories: List<String>,
    onCategoryAdded: (String) -> Unit,
    onCategoryRemoved: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var newCategoryName by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("") }
    var customCategories by remember { mutableStateOf<List<PredefinedCategory>>(emptyList()) }

    val predefinedCategories = listOf(
        PredefinedCategory(name = stringResource(R.string.category_coffee), emoji = "☕"),
        PredefinedCategory(name = stringResource(R.string.category_hot_beverages), emoji = "🔥"),
        PredefinedCategory(name = stringResource(R.string.category_cold_beverages), emoji = "❄️"),
        PredefinedCategory(name = stringResource(R.string.category_desserts), emoji = "🍰"),
        PredefinedCategory(name = stringResource(R.string.category_cakes_pastries), emoji = "🎂"),
        PredefinedCategory(name = stringResource(R.string.category_cookies_snacks), emoji = "🍪"),
        PredefinedCategory(name = stringResource(R.string.category_breakfast), emoji = "🍳"),
        PredefinedCategory(name = stringResource(R.string.category_toasts_sandwiches), emoji = "🥪"),
        PredefinedCategory(name = stringResource(R.string.category_salads), emoji = "🥗"),
        PredefinedCategory(name = stringResource(R.string.category_main_dishes), emoji = "🍛"),
        PredefinedCategory(name = stringResource(R.string.category_burgers), emoji = "🍔"),
        PredefinedCategory(name = stringResource(R.string.category_pasta), emoji = "🍝"),
        PredefinedCategory(name = stringResource(R.string.category_smoothies_detox), emoji = "🍓"),
        PredefinedCategory(name = stringResource(R.string.category_fresh_juices), emoji = "🍹"),
        PredefinedCategory(name = stringResource(R.string.category_milkshakes), emoji = "🥤"),
        PredefinedCategory(name = stringResource(R.string.category_herbal_teas), emoji = "🍵"),
        PredefinedCategory(name = stringResource(R.string.category_special_blends), emoji = "🧉"),
        PredefinedCategory(name = stringResource(R.string.category_vegan_gluten_free), emoji = "🌱"),
        PredefinedCategory(name = stringResource(R.string.category_kids_menu), emoji = "🧒"),
        PredefinedCategory(name = stringResource(R.string.category_chef_specials), emoji = "🌟")
    )

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
                val isSelected = categories.contains("${category.emoji} ${category.name}")
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            if (isSelected) {
                                onCategoryRemoved("${category.emoji} ${category.name}")
                            } else {
                                onCategoryAdded("${category.emoji} ${category.name}")
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
                            text = category.emoji,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = category.name,
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
                        .height(100.dp)
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

        // Navigation buttons
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
        Dialog(onDismissRequest = { showAddDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.add_custom_category),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = newCategoryName,
                        onValueChange = { newCategoryName = it },
                        label = { Text(stringResource(R.string.category_name_label)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.select_emoji),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    ) {
                        items(commonEmojis) { emoji ->
                            Surface(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable { selectedEmoji = emoji }
                                    .border(
                                        width = 1.dp,
                                        color = if (selectedEmoji == emoji)
                                            MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                color = if (selectedEmoji == emoji) 
                                       MaterialTheme.colorScheme.primaryContainer 
                                       else MaterialTheme.colorScheme.surface
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = emoji,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { 
                                showAddDialog = false
                                newCategoryName = ""
                                selectedEmoji = ""
                            }
                        ) {
                            Text(stringResource(R.string.dialog_cancel))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (newCategoryName.isNotEmpty() && selectedEmoji.isNotEmpty()) {
                                    customCategories = customCategories + PredefinedCategory(
                                        name = newCategoryName,
                                        emoji = selectedEmoji
                                    )
                                    onCategoryAdded("$selectedEmoji $newCategoryName")
                                    showAddDialog = false
                                    newCategoryName = ""
                                    selectedEmoji = ""
                                }
                            },
                            enabled = newCategoryName.isNotEmpty() && selectedEmoji.isNotEmpty()
                        ) {
                            Text(stringResource(R.string.dialog_add))
                        }
                    }
                }
            }
        }
    }
}