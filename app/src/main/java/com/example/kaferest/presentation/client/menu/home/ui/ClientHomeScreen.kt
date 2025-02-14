package com.example.kaferest.presentation.client.menu.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.kaferest.R
import com.example.kaferest.presentation.client.nav.viewmodel.ClientMainViewModel
import com.example.kaferest.presentation.menu.home.ui.Category
import com.example.kaferest.presentation.menu.home.ui.CategoryItem
import com.example.kaferest.presentation.menu.home.ui.ShopCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.kaferest.presentation.navigation.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.TextButton
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientHomeScreen(
    navController: NavController,
    homeViewModel: ClientMainViewModel,
) {
    val state by homeViewModel.state.collectAsState()

    // Sample carousel items
    val carouselItems = listOf(
        "https://picsum.photos/800/400",
        "https://picsum.photos/800/401",
        "https://picsum.photos/800/402",
        "https://picsum.photos/800/403"
    )
    val categories = listOf(
        Category(painterResource(R.drawable.cappuccino) , stringResource(R.string.cappuccino)),
        Category(painterResource(R.drawable.espresso), stringResource(R.string.espresso)),
        Category(painterResource(R.drawable.latte), stringResource(R.string.latte)),
        Category(painterResource(R.drawable.macchiato), stringResource(R.string.macchiato)),
        Category(painterResource(R.drawable.mocha), stringResource(R.string.mocha)),
        Category(painterResource(R.drawable.waffles), stringResource(R.string.waffles)),
        Category(painterResource(R.drawable.pancakes), stringResource(R.string.pancakes)),
        Category(painterResource(R.drawable.croissant), stringResource(R.string.croissant)),
        Category(painterResource(R.drawable.simit), stringResource(R.string.simit))
    )

    val pagerState = rememberPagerState(pageCount = { carouselItems.size })
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    // Auto-scroll effect
    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % carouselItems.size
            scope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Enhanced Search Bar
                item {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = { /* TODO: Handle search */ },
                        active = isSearchActive,
                        onActiveChange = { isSearchActive = it },
                        placeholder = { Text(stringResource(R.string.search_hint)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Search suggestions
                        repeat(3) { index ->
                            ListItem(
                                headlineContent = { 
                                    Text(stringResource(R.string.recent_search, index + 1))
                                },
                                leadingContent = { Icon(Icons.Default.MoreVert, contentDescription = null) }
                            )
                        }
                    }
                }

                // Quick Filters
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item { FilterChip(
                            selected = false,
                            onClick = { /* TODO */ },
                            label = { Text(stringResource(R.string.near_me)) },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
                        )}
                        item { FilterChip(
                            selected = false,
                            onClick = { /* TODO */ },
                            label = { Text(stringResource(R.string.open_now)) }
                        )}
                        item { FilterChip(
                            selected = false,
                            onClick = { /* TODO */ },
                            label = { Text(stringResource(R.string.top_rated)) }
                        )}
                    }
                }

                // Carousel
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                        ) { page ->
                            AsyncImage(
                                model = carouselItems[page],
                                contentDescription = "Carousel image $page",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Indicators
                        Row(
                            Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(carouselItems.size) { iteration ->
                                val color = if (pagerState.currentPage == iteration) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(8.dp)
                                )
                            }
                        }
                    }
                }

                // Categories Section with header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.categories),
                            style = MaterialTheme.typography.titleLarge
                        )
                        TextButton(onClick = { /* TODO: Show all categories */ }) {
                            Text(stringResource(R.string.see_all))
                        }
                    }
                }

                // Categories List
                item {
                    LazyRow(
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(categories) { category ->
                            CategoryItem(category)
                        }
                    }
                }

                // Today's Specials
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.todays_specials),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = stringResource(R.string.special_offer_text),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Button(
                                onClick = { /* TODO: Show specials */ },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(stringResource(R.string.view_offers))
                            }
                        }
                    }
                }

                // Featured Cafes with header
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.featured_cafes),
                            style = MaterialTheme.typography.titleLarge
                        )
                        TextButton(onClick = { /* TODO: Show all cafes */ }) {
                            Text(stringResource(R.string.see_all))
                        }
                    }
                }

                // Featured Cafes Grid
                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(((state.shops.take(5).size + 1) / 2 * 200).dp)
                    ) {
                        items(state.shops.take(5)) { shop ->
                            if (shop.shopName.isNotEmpty()) {
                                ShopCard(
                                    shop = shop,
                                    onClick = { 
                                        homeViewModel.selectShop(shop)
                                        navController.navigate(Screen.ClientShopDetailScreen.createRoute(shop.shopName))
                                    }
                                )
                            }
                        }
                    }
                }

                // Near You Section
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.near_you),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.map_preview))
                        }
                    }
                }
            }
        }
    }
}




