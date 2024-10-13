package com.example.cafely.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import com.example.cafely.ui.theme.Coffee
import com.example.cafely.ui.theme.CoffeeBlack
import com.example.cafely.ui.theme.CoffeeDark

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

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isOutlined: Boolean = false,
    buttonContent: ButtonContent,
    text: String,
    style: TextStyle
) {
    if (isOutlined) {
        OutlinedButton(onClick = onClick, modifier = modifier, border = BorderStroke(width = 1.dp, color = CoffeeBlack)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = text, style = style, color = CoffeeBlack)
                Spacer(modifier = Modifier.width(8.dp))
                when (buttonContent) {
                    is ButtonContent.IconContent -> {
                        androidx.compose.material3.Icon(
                            imageVector = buttonContent.imageVector,
                            contentDescription = buttonContent.contentDescription,
                            tint = CoffeeBlack
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
            colors = ButtonColors(
                contentColor = Coffee,
                containerColor = CoffeeDark,
                disabledContainerColor = CoffeeDark,
                disabledContentColor = Coffee),
            modifier = modifier
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
