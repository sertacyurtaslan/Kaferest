package com.example.kaferest.util

import android.annotation.SuppressLint
import android.util.Log
import com.example.kaferest.data.credentials.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object AmazonSESUtil {
    private const val TAG = "AmazonSESUtil"

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
                put("mail.transport.protocol", "smtp")
                put("mail.smtp.port", Credentials.SMTP_PORT)
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.starttls.required", "true")
                put("mail.smtp.auth", "true")
                put("mail.smtp.ssl.protocols", "TLSv1.2")
                put("mail.smtp.host", Credentials.SMTP_HOST)
                put("mail.debug", "true")
            }

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(Credentials.ACCESS_KEY, Credentials.SECRET_KEY)
                }
            })

            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(Credentials.FROM_EMAIL, Credentials.FROM_NAME))
                setRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
                subject = "Welcome to Cafely - Verify Your Email"
                setContent(EmailTemplate.getVerificationEmailTemplate(verificationCode), "text/html; charset=utf-8")
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

    @SuppressLint("NewApi")
    private fun getCurrentTimestamp(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    private fun getBackoffDelay(attempt: Int): Long {
        return (Math.pow(2.0, attempt.toDouble()) * 1000).toLong().coerceAtMost(30000)
    }
} 