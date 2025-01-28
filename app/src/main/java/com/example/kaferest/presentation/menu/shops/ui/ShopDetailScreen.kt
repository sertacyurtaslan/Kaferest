package com.example.kaferest.presentation.menu.shops.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kaferest.presentation.menu.shops.viewmodel.ShopsViewModel

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailScreen(
    shopId: String,
    navController: NavController,
    viewModel: ShopsViewModel = hiltViewModel()
) {
    // Sample menu items
    val menuItems = remember {
        listOf(
            MenuItem(
                id = "1",
                name = "Cappuccino",
                description = "Classic Italian coffee drink",
                price = 4.99,
                image = "https://picsum.photos/200/200",
                category = "Coffee"
            ),
            MenuItem(
                id = "2",
                name = "Croissant",
                description = "Buttery, flaky pastry",
                price = 3.99,
                image = "https://picsum.photos/200/201",
                category = "Pastries"
            ),
            MenuItem(
                id = "3",
                name = "Avocado Toast",
                description = "Fresh avocado on sourdough",
                price = 8.99,
                image = "https://picsum.photos/200/202",
                category = "Breakfast"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Shop Header
            item {
                AsyncImage(
                    model = "https://picsum.photos/800/400",
                    contentDescription = "Shop Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Coffee House",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Address Section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "123 Coffee Street, Cafe District",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                    //Rating Secion
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)

                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "4.5 (128 reviews)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }


                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$$$ • Coffee, Breakfast, Lunch",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

            // Menu Categories
            item {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("All", "Coffee", "Pastries", "Breakfast", "Lunch")) { category ->
                        FilterChip(
                            selected = category == "All",
                            onClick = { /* Handle category selection */ },
                            label = { Text(category) }
                        )
                    }
                }
            }

            // Menu Items
            items(menuItems) { menuItem ->
                MenuItemCard(menuItem)
            }

        }
    }
}


