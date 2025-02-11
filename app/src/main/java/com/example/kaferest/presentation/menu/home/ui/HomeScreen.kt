package com.example.kaferest.presentation.menu.home.ui

import com.example.kaferest.presentation.menu.home.viewmodel.HomeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.kaferest.R
import com.example.kaferest.domain.model.Shop
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Search Bar
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Carousel
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

                Spacer(modifier = Modifier.height(24.dp))

                // Categories
                Text(
                    stringResource(R.string.categories),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categories) { category ->
                        CategoryItem(category)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Featured Cafes
                Text(
                    stringResource(R.string.featured_cafes),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.shops.take(5)) { shop ->
                        ShopCard(
                            shop = shop,
                            onClick = { homeViewModel.selectShop(shop) }
                        )
                    }
                }
            }
        }
    }
}




