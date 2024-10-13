package com.example.financecompose.presentation.entrance.forgot_password.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun PasswordResetDialog(
    onDismiss: () -> Unit = {},
    onReturnSignIn: () -> Unit = {},
    onResendEmail: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties =
            DialogProperties(
                usePlatformDefaultWidth = false
            ),
        content = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier // No additional modifier needed
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                    Text(
                        text = "We've sent a password reset email.",
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Check your inbox for an email from Financely and follow the instructions to reset your password.",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = onReturnSignIn,
                            modifier = Modifier
                                .padding(end = 8.dp, start = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Back to Sign In")
                        }
                        OutlinedButton(
                            onClick = onResendEmail,
                            modifier = Modifier
                                .padding(end = 8.dp, start = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Resend Email")
                        }
                    }
                }
            }

        })
}




@Preview(showBackground = true)
@Composable
fun PreviewPasswordResetDialog() {
    PasswordResetDialog()
}