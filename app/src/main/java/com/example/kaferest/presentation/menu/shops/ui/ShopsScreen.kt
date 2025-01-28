package com.example.kaferest.presentation.menu.shops.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kaferest.presentation.menu.shops.viewmodel.ShopsViewModel
import com.example.kaferest.presentation.navigation.Screen

data class Shop(
    val id: String,
    val name: String,
    val image: String,
    val rating: Float,
    val totalRatings: Int,
    val description: String,
    val categories: List<String>,
    val priceRange: String,
    val address: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopsScreen(
    navController: NavController,
    viewModel: ShopsViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }

    // Sample shops data
    val shops = remember {
        listOf(
            Shop(
                id = "1",
                name = "Coffee House",
                image = "https://picsum.photos/400/300",
                rating = 4.5f,
                totalRatings = 128,
                description = "Cozy coffee shop with great ambiance",
                categories = listOf("Coffee", "Breakfast", "Desserts"),
                priceRange = "$$",
                address = "789 Bağdat Caddesi, Kadıköy, Istanbul"
            ),
            Shop(
                id = "2",
                name = "Brew & Bites",
                image = "https://picsum.photos/400/301",
                rating = 4.2f,
                totalRatings = 85,
                description = "Artisanal coffee and fresh pastries",
                categories = listOf("Coffee", "Pastries", "Sandwiches"),
                priceRange = "$",
                address = "123 İstiklal Caddesi, Beyoğlu, Istanbul"
            ),
            Shop(
                id = "3",
                name = "The Daily Grind",
                image = "https://picsum.photos/400/302",
                rating = 4.8f,
                totalRatings = 256,
                description = "Premium coffee and light meals",
                categories = listOf("Coffee", "Lunch", "Smoothies"),
                priceRange = "$$$",
                address = "456 Nişantaşı Mahallesi, Şişli, Istanbul"
            ),
            Shop(
                id = "4",
                name = "Bean Scene",
                image = "https://picsum.photos/400/303",
                rating = 4.3f,
                totalRatings = 164,
                description = "Specialty coffee and brunch spot",
                categories = listOf("Coffee", "Brunch", "Tea"),
                priceRange = "$$",
                address = "321 Cihangir Sokak, Beyoğlu, Istanbul"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Search cafes...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        )

        // Shops Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(shops.filter { 
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.categories.any { cat -> cat.contains(searchQuery, ignoreCase = true) }
            }) { shop ->
                ShopCard(
                    shop = shop,
                    onShopClick = {
                            navController.navigate(Screen.ShopDetail.createRoute(shop.id))
                    }
                )
            }
        }
    }
}

@Composable
fun ShopCard(
    shop: Shop,
    onShopClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onShopClick),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column {
            // Shop Image
            AsyncImage(
                model = shop.image,
                contentDescription = shop.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            // Shop Info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = shop.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating and Price Range
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${shop.rating} (${shop.totalRatings})",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = shop.priceRange,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Categories
                Text(
                    text = shop.categories.joinToString(" • "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1
                )
            }
        }
    }
}
