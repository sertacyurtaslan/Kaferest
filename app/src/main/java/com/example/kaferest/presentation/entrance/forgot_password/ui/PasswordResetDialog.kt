package com.example.kaferest.presentation.entrance.forgot_password.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kaferest.R
import com.example.kaferest.presentation.entrance.forgot_password.viewmodel.ForgotPasswordViewModel
import com.example.kaferest.ui.theme.Typography

@Composable
fun PasswordResetDialog(
    onDismiss: () -> Unit = {},
    onReturnSignIn: () -> Unit = {},
    onResendEmail: () -> Unit = {},
    viewModel: ForgotPasswordViewModel
) {

    val canResend by viewModel.canResend.collectAsState()
    val remainingSeconds by viewModel.remainingSeconds.collectAsState()

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
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                    Text(
                        text = stringResource(R.string.we_ve_sent_a_password_reset_email),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.check_your_inbox),
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
                            enabled = canResend,
                            modifier = Modifier
                                .padding(end = 8.dp, start = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = if (canResend) {
                                    stringResource(R.string.resend_code)
                                } else {
                                    stringResource(R.string.resend_code_in_seconds, remainingSeconds)
                                },
                                style = Typography.bodyMedium,
                                color = if (canResend) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                                }
                            )
                        }
                    }
                }
            }
        })
}
