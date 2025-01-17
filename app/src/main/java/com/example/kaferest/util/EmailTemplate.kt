package com.example.kaferest.util

object EmailTemplate {
    fun getVerificationEmailTemplate(verificationCode: String): String = """
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Email Verification</title>
          <style>
            body {
              font-family: 'Arial', sans-serif;
              background-color: #f7efe5;
              margin: 0;
              padding: 0;
            }
            .email-container {
              max-width: 600px;
              margin: 20px auto;
              background-color: #ffffff;
              border-radius: 8px;
              overflow: hidden;
              box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            .email-header {
              background-color: #d4a373;
              color: #ffffff;
              text-align: center;
              padding: 20px;
            }
            .email-header h1 {
              margin: 0;
              font-size: 28px;
            }
            .email-body {
              padding: 20px;
              text-align: center;
              color: #4a3f35;
            }
            .email-body p {
              font-size: 16px;
              line-height: 1.6;
              margin: 10px 0;
            }
            .verification-code {
              font-size: 36px;
              font-weight: bold;
              color: #d4a373;
              background-color: #f7efe5;
              border: 2px dashed #d4a373;
              display: inline-block;
              padding: 10px 20px;
              border-radius: 5px;
              margin: 20px 0;
            }
            .coffee-icon {
              font-size: 48px;
              margin: 20px 0;
            }
            .email-footer {
              background-color: #f7efe5;
              text-align: center;
              padding: 15px;
              font-size: 12px;
              color: #7a6a5f;
            }
            .email-footer a {
              color: #d4a373;
              text-decoration: none;
            }
          </style>
        </head>
        <body>
          <div class="email-container">
            <div class="email-header">
              <h1>Welcome to Cafely</h1>
            </div>
            <div class="email-body">
              <div class="coffee-icon">☕</div>
              <p>Hi Coffee Lover,</p>
              <p>We're thrilled to have you join our cafe community! To enjoy your first cup of coffee with us, please verify your email address by using the code below:</p>
              <div class="verification-code">$verificationCode</div>
              <p>Not your email? No worries, you can ignore this email.</p>
              <p>Warm regards,<br>The Cafely Team</p>
            </div>
            <div class="email-footer">
              <p>Need help? <a href="mailto:support@cafename.com">Contact Support</a></p>
              <p>&copy; 2025 Cafely. All rights reserved.</p>
            </div>
          </div>
        </body>
        </html>
    """.trimIndent()
} 