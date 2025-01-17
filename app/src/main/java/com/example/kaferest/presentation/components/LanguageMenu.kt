package com.example.kaferest.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kaferest.R
import com.example.kaferest.ui.theme.Typography

@Composable
fun LanguageMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .width(180.dp)
    ) {
        // English Option
        DropdownMenuItem(
            text = {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.flag_en),
                        contentDescription = "English Flag",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.english),
                        style = Typography.bodyMedium
                    )
                }
            },
            onClick = {
                onLanguageSelected("en")
                onDismissRequest()
            }
        )

        // Turkish Option
        DropdownMenuItem(
            text = {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.flag_tr),
                        contentDescription = "Turkish Flag",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.turkish),
                        style = Typography.bodyMedium
                    )
                }
            },
            onClick = {
                onLanguageSelected("tr")
                onDismissRequest()
            }
        )
    }
} 