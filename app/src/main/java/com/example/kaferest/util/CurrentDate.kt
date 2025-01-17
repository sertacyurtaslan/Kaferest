package com.example.kaferest.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CurrentDate {
    private val currentDate = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val formattedDate = dateFormat.format(currentDate.time)

    // Function to return the formatted date
    fun getFormattedDate(): String {
        return formattedDate
    }
}
