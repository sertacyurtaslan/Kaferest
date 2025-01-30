package com.example.kaferest.presentation.shop.menu.home.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kaferest.R
import com.example.kaferest.ui.theme.Typography
import org.burnoutcrew.reorderable.*

@Composable
fun PhotoStep(
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var selectedPhotos by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            val newPhotos = (selectedPhotos + uris).distinct().take(3)
            selectedPhotos = newPhotos
        }
    }

    val state = rememberReorderableLazyGridState(
        onMove = { from, to ->
            selectedPhotos = selectedPhotos.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.add_photos),
            style = Typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = state.gridState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedPhotos, { it.toString() }) { photo ->
                ReorderableItem(state, key = photo.toString()) { isDragging ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = if (isDragging) 2.dp else 1.dp,
                                color = if (isDragging) MaterialTheme.colorScheme.primary 
                                       else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(photo)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        
                        // Remove button
                        IconButton(
                            onClick = {
                                selectedPhotos = selectedPhotos - photo
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(24.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = stringResource(R.string.remove_photo),
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        // Drag handle
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.drag_to_reorder),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(4.dp)
                                .size(24.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp)
                        )
                    }
                }
            }

            if (selectedPhotos.size < 3) {
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                photoPickerLauncher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_photo),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        if (selectedPhotos.isNotEmpty()) {
            Text(
                text = stringResource(R.string.drag_to_reorder_hint),
                style = Typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
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
                onClick = {
                    onNext()
                },
                enabled = selectedPhotos.isNotEmpty()
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}