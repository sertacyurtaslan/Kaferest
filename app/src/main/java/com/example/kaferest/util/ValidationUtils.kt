package com.example.kaferest.util

import android.util.Patterns

object ValidationUtils {
    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 10 && password.contains(Regex("[^A-Za-z0-9]"))
    }

    fun validateLoginForm(email: String, password: String): Boolean {
        return validateEmail(email) && validatePassword(password)
                && email.isNotEmpty() && password.isNotEmpty()
    }

    fun validateRegistrationForm(
        name: String,
        email: String,
        password: String
    ): Boolean {
        return name.isNotEmpty() &&
                validateEmail(email) && email.isNotEmpty() &&
                validatePassword(password) && password.isNotEmpty()
    }

}