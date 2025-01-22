package com.example.kaferest.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isOutlined: Boolean = false,
    enabled: Boolean = true,
    buttonContent: ButtonContent,
    text: String,
    style: TextStyle
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = text, style = style)
                Spacer(modifier = Modifier.width(8.dp))
                when (buttonContent) {
                    is ButtonContent.IconContent -> {
                        androidx.compose.material3.Icon(
                            imageVector = buttonContent.imageVector,
                            contentDescription = buttonContent.contentDescription,
                        )
                    }

                    is ButtonContent.ImageContent -> {
                        androidx.compose.foundation.Image(
                            painter = buttonContent.painter,
                            contentDescription = buttonContent.contentDescription,
                            modifier = buttonContent.imageModifier
                        )
                    }
                }
            }
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (enabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = text, style = style)
                Spacer(modifier = Modifier.width(8.dp))
                when (buttonContent) {
                    is ButtonContent.IconContent -> {
                        androidx.compose.material3.Icon(
                            imageVector = buttonContent.imageVector,
                            contentDescription = buttonContent.contentDescription,
                        )
                    }

                    is ButtonContent.ImageContent -> {
                        androidx.compose.foundation.Image(
                            painter = buttonContent.painter,
                            contentDescription = buttonContent.contentDescription,
                            modifier = buttonContent.imageModifier
                        )
                    }
                }
            }
        }
    }
}
sealed class ButtonContent {

    data class IconContent(
        val imageVector: ImageVector,
        val contentDescription: String?,
        val tint: Color = Color.Unspecified) : ButtonContent()

    data class ImageContent(
        val painter: Painter,
        val contentDescription: String,
        val imageModifier: Modifier
    ) : ButtonContent()
}
