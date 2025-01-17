package com.example.kaferest.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.kaferest.R
import com.example.kaferest.ui.theme.Typography

@Composable
fun PasswordValidator(
    stringState: MutableState<String>,
) {
    var isLengthValid by remember { mutableStateOf(false) }
    var containsSpecialChar by remember { mutableStateOf(false) }

    val password = stringState.value

    LaunchedEffect(password) {
        isLengthValid = password.length >= 10
        containsSpecialChar = password.contains(Regex("[^A-Za-z0-9]"))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Icon(
            imageVector = if (isLengthValid)  Icons.Outlined.CheckCircle else Icons.Outlined.Warning,
            contentDescription = stringResource(R.string.password_valid),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.at_least_10_characters),
            style = Typography.titleSmall
        )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Icon(
            imageVector = if (containsSpecialChar) Icons.Outlined.CheckCircle else Icons.Outlined.Warning,
            contentDescription = stringResource(R.string.password_invalid),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.contains_a_special_character),
            style = Typography.titleSmall

        )
    }
}

