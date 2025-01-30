package com.example.kaferest.presentation.shop.menu.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kaferest.R
import com.example.kaferest.presentation.components.CustomIconTextField
import com.example.kaferest.ui.theme.Typography

@Composable
fun BasicInfoStep(
    onNext: (String) -> Unit
) {
    val shopNameState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CustomIconTextField(
            stringState = shopNameState,
            text = stringResource(R.string.shop_name),
            style = Typography.titleMedium,
            textType = KeyboardType.Text,
            leadingIcon = Icons.Default.Home,
            leadIconDesc = stringResource(R.string.lock_icon),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNext(shopNameState.value) },
            enabled = shopNameState.value.isNotEmpty(),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.next))
        }
    }
}