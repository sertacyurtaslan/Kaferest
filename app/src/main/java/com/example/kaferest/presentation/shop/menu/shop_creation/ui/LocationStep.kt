package com.example.kaferest.presentation.shop.menu.shop_creation.ui

import android.Manifest
import android.location.LocationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kaferest.R
import com.example.kaferest.presentation.components.CustomIconTextField
import com.example.kaferest.ui.theme.Typography
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LocationStep(
    onLocationPermissionGranted: () -> Unit,
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val addressState = remember { mutableStateOf("") }
    var showMap by remember { mutableStateOf(false) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var showLocationServicesDialog by remember { mutableStateOf(false) }
    var showPermissionDeniedDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var mapError by remember { mutableStateOf<String?>(null) }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Check if location services are enabled
    fun checkLocationServices(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // Request location updates
    fun requestLocation() {
        if (!checkLocationServices()) {
            showLocationServicesDialog = true
            return
        }

        isLoading = true
        try {
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    userLocation = LatLng(location.latitude, location.longitude)
                    showMap = true
                    mapError = null
                } else {
                    mapError = "Could not get location. Please try again."
                }
            }
        } catch (e: SecurityException) {
            isLoading = false
            mapError = "Location permission denied"
            showPermissionDeniedDialog = true
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requestLocation()
            onLocationPermissionGranted()
        } else {
            showPermissionDeniedDialog = true
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

        if (mapError != null) {
            Text(
                text = mapError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.detect_location))
            }
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

    // Location Services Dialog
    if (showLocationServicesDialog) {
        AlertDialog(
            onDismissRequest = { showLocationServicesDialog = false },
            title = { Text("Location Services Required") },
            text = { Text("Please enable location services to use this feature.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLocationServicesDialog = false
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ) {
                    Text("Enable")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLocationServicesDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Permission Denied Dialog
    if (showPermissionDeniedDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDeniedDialog = false },
            title = { Text("Permission Required") },
            text = { Text("Location permission is required to detect your location. Please grant the permission in app settings.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDeniedDialog = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = android.net.Uri.fromParts("package", context.packageName, null)
                        context.startActivity(intent)
                    }
                ) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDeniedDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Map Dialog
    if (showMap && userLocation != null) {
        AlertDialog(
            onDismissRequest = { showMap = false },
            title = { Text("Select Location") },
            text = {
                Box(
                    modifier = Modifier
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
                        )
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
                        scope.launch {
                            try {
                                val geocoder = android.location.Geocoder(context)
                                val addresses = geocoder.getFromLocation(
                                    userLocation!!.latitude,
                                    userLocation!!.longitude,
                                    1
                                )
                                if (addresses?.isNotEmpty() == true) {
                                    val address = addresses[0]
                                    addressState.value = address.getAddressLine(0)
                                }
                            } catch (e: Exception) {
                                mapError = "Could not get address. Please enter manually."
                            }
                            showMap = false
                        }
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