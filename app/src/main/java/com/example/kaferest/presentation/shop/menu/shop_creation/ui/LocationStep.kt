package com.example.kaferest.presentation.admin.menu.shop_creation.ui

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kaferest.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.google.android.gms.location.LocationServices
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.text.input.KeyboardType
import com.example.kaferest.presentation.components.CustomIconTextField
import com.example.kaferest.ui.theme.Typography
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun LocationStep(
    onLocationPermissionGranted: () -> Unit,
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {
    val addressState = remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showMap by remember { mutableStateOf(false) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scope.launch {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            userLocation = LatLng(location.latitude, location.longitude)
                            showMap = true
                        }
                    }
                } catch (e: SecurityException) {
                    // Handle permission denied
                }
            }
            onLocationPermissionGranted()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        CustomIconTextField(
            stringState = addressState,
            text = stringResource(R.string.address),
            style = Typography.titleMedium,
            textType = KeyboardType.Text,
            leadingIcon = Icons.Default.LocationOn,
            leadIconDesc = stringResource(R.string.lock_icon),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.detect_location))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.back))
            }
            Button(
                onClick = { onNext(addressState.value) },
                enabled = addressState.value.isNotEmpty()
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }

    if (showMap && userLocation != null) {
        AlertDialog(
            onDismissRequest = { showMap = false },
            title = { Text("Select Location") },
            text = {
                Box(modifier = Modifier
                    .size(400.dp)
                    .fillMaxWidth()
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(userLocation!!, 15f)
                        },
                        properties = MapProperties(
                            isMyLocationEnabled = true,
                            mapType = MapType.NORMAL,
                            isIndoorEnabled = true
                        ),
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = true,
                            myLocationButtonEnabled = true,
                            mapToolbarEnabled = true
                        ),
                        onMapLoaded = {
                            // Map is loaded and ready
                            println("Map loaded successfully")
                        }
                    ) {
                        Marker(
                            state = MarkerState(position = userLocation!!),
                            title = "Your Location"
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Get address from location using Geocoder
                        val geocoder = android.location.Geocoder(context)
                        try {
                            val addresses = geocoder.getFromLocation(
                                userLocation!!.latitude,
                                userLocation!!.longitude,
                                1
                            )
                            if (addresses?.isNotEmpty() == true) {
                                val address = addresses[0]
                                val addressText = address.getAddressLine(0)
                                addressState.value = addressText //Hope it works
                            }
                        } catch (e: Exception) {
                            // Handle geocoding error
                        }
                        showMap = false
                    }
                ) {
                    Text("Confirm Location")
                }
            },
            dismissButton = {
                TextButton(onClick = { showMap = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}