package com.example.kaferest.presentation.menu.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kaferest.R
import com.example.kaferest.presentation.menu.settings.viewmodel.SettingsViewModel
import com.example.kaferest.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by viewModel.settingsState.collectAsState()
    val scrollState = rememberScrollState()
    var showSignOutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSignedOut) {
        if (state.isSignedOut) {
            navController.navigate(Screen.IntroScreen.route) {
                popUpTo(Screen.MainScreen.route) { inclusive = true }
            }
        }
    }

        Scaffold{ paddingValues ->
            Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            // Profile Section
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_profile_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ListItem(
                        headlineContent = { 
                            Text(
                                state.userName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        supportingContent = { 
                            Text(
                                state.userEmail,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = stringResource(R.string.cd_profile),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { /* Navigate to profile edit */ }
                    )
                }
            }

            // Appearance Section
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_appearance_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Theme Switch
                    ListItem(
                        headlineContent = { 
                            Text(
                                stringResource(R.string.settings_dark_theme),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.isDarkMode,
                                onCheckedChange = { viewModel.toggleTheme() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        },
                        leadingContent = {
                            Icon(
                                painterResource(if (state.isDarkMode) R.drawable.dark_mode else R.drawable.light_mode),
                                contentDescription = stringResource(R.string.cd_theme),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    )

                    // Language Selection
                    var showLanguageDialog by remember { mutableStateOf(false) }
                    ListItem(
                        headlineContent = { 
                            Text(
                                stringResource(R.string.language),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingContent = {
                            Text(
                                stringResource(R.string.selected_language),
                                modifier = Modifier.padding(end = 5.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        },
                        leadingContent = {
                            Icon(
                                painterResource(id = R.drawable.baseline_language_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { showLanguageDialog = true }
                    )

                    if (showLanguageDialog) {
                        LanguageSelectionDialog(
                            selectedLanguage = state.selectedLanguage,
                            onDismiss = { showLanguageDialog = false },
                            context = context
                        )
                    }
                }
            }

            // Notifications Section
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_notifications_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ListItem(
                        headlineContent = { 
                            Text(
                                stringResource(R.string.settings_push_notifications),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.notificationsEnabled,
                                onCheckedChange = { viewModel.toggleNotifications() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = stringResource(R.string.cd_notifications),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    )
                }
            }

            // About Section
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_about_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ListItem(
                        headlineContent = { 
                            Text(
                                stringResource(R.string.settings_privacy_policy),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = stringResource(R.string.cd_privacy),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { /* Navigate to privacy policy */ }
                    )
                    ListItem(
                        headlineContent = { 
                            Text(
                                stringResource(R.string.settings_terms_of_service),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = stringResource(R.string.cd_terms),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { /* Navigate to terms */ }
                    )
                    ListItem(
                        headlineContent = { 
                            Text(
                                stringResource(R.string.settings_app_version),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        supportingContent = { 
                            Text(
                                "1.0.0",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = stringResource(R.string.cd_version),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    )
                }
            }

            // Sign Out Button
            Button(
                onClick = { showSignOutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = stringResource(R.string.cd_sign_out),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    stringResource(R.string.settings_sign_out),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            if (showSignOutDialog) {
                AlertDialog(
                    onDismissRequest = { showSignOutDialog = false },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.settings_sign_out),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            IconButton(onClick = { showSignOutDialog = false }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.cd_close_dialog),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    text = {
                        Text(
                            stringResource(R.string.settings_sign_out_confirmation),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                scope.launch {
                                    viewModel.signOut()
                                }
                                showSignOutDialog = false
                            }
                        ) {
                            Text(stringResource(R.string.settings_sign_out))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showSignOutDialog = false }) {
                            Text(stringResource(R.string.settings_cancel))
                        }
                    }
                )
            }
        }
    }
}
