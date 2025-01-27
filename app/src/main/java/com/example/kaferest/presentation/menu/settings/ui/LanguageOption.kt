package com.example.kaferest.presentation.menu.settings.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kaferest.MainActivity
import com.example.kaferest.R

@Composable
fun LanguageOption(
    language: String,
    selectedLanguage: String,
    languageNameRes: Int,
    context: Context,
    onDismiss: () -> Unit
) {
    // Add logging to debug
    println("LanguageOption - language: $language, selected: $selectedLanguage")


    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable {
                onDismiss()
                (context as MainActivity).changeLocaleAndRecreate(language, applyOnly = true)
                context.window.decorView.postDelayed({
                    context.recreate()
                }, 150)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (selectedLanguage == language) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selectedLanguage == language) 4.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(languageNameRes),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (language == selectedLanguage) FontWeight.Bold else FontWeight.Normal,
                    color = if (language == selectedLanguage) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            )
            if (language == selectedLanguage) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.language),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}