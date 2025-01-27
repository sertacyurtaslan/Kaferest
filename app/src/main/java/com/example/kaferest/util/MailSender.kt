package com.example.kaferest.util

import android.util.Log
import com.example.kaferest.data.credentials.Credentials.APP_NAME
import com.example.kaferest.data.credentials.Credentials.SMTP_EMAIL
import com.example.kaferest.data.credentials.Credentials.SMTP_HOST
import com.example.kaferest.data.credentials.Credentials.SMTP_PASSWORD
import com.example.kaferest.data.credentials.Credentials.SMTP_PORT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailSender {
    private const val TAG = "EmailService"

    suspend fun sendVerificationEmail(
        toEmail: String,
        verificationCode: String,
        retryCount: Int = 3
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            require(isValidEmail(toEmail)) { "Invalid email format: $toEmail" }
            require(verificationCode.isNotBlank()) { "Verification code cannot be empty" }

            Log.d(TAG, "Sending verification email to: $toEmail")

            val props = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", SMTP_HOST)
                put("mail.smtp.port", SMTP_PORT)
                put("mail.smtp.ssl.protocols", "TLSv1.2")
                put("mail.smtp.ssl.trust", SMTP_HOST)
            }

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(SMTP_EMAIL, SMTP_PASSWORD)
                }
            })

            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(SMTP_EMAIL, APP_NAME))
                setRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
                subject = "Welcome to Kaferest - Verify Your Email"
                setContent(VerificationMail.getVerificationEmailTemplate(verificationCode), "text/html; charset=utf-8")
            }

            var attemptCount = 0
            var lastException: Exception? = null

            while (attemptCount < retryCount) {
                try {
                    Transport.send(message)
                    Log.i(TAG, "Email sent successfully to $toEmail")
                    return@withContext Result.success(true)
                } catch (e: Exception) {
                    Log.e(TAG, "Attempt ${attemptCount + 1} failed: ${e.message}")
                    lastException = e
                    attemptCount++
                    
                    if (attemptCount < retryCount) {
                        kotlinx.coroutines.delay(getBackoffDelay(attemptCount))
                    }
                }
            }

            Result.failure(lastException ?: RuntimeException("Failed to send email after $retryCount attempts"))
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Validation error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error: ${e.message}", e)
            Result.failure(RuntimeException("Unexpected error while sending email", e))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun getBackoffDelay(attempt: Int): Long {
        return (Math.pow(2.0, attempt.toDouble()) * 1000).toLong().coerceAtMost(30000)
    }
} 