package com.example.kaferest.presentation.shop.menu.shop_creation.ui.categories

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kaferest.R
import com.example.kaferest.data.constants.EmojiConstants
import com.example.kaferest.domain.model.Category

@Composable
fun AddCustomCategoryDialog(
    onDismiss: () -> Unit,
    onCategoryAdded: (Category) -> Unit
) {
    var newCategoryName by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
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
                    items(EmojiConstants.ALL_EMOJIS) { emoji ->
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
                        onClick = onDismiss
                    ) {
                        Text(stringResource(R.string.dialog_cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (newCategoryName.isNotEmpty() && selectedEmoji.isNotEmpty()) {
                                val category = Category(
                                        categoryName = newCategoryName,
                                        categoryEmoji = selectedEmoji
                                )
                                onCategoryAdded(category)
                                onDismiss()
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