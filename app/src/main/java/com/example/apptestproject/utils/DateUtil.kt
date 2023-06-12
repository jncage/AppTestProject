package com.example.apptestproject.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {
    private const val DATE_FORMAT = "dd MMMM, yyyy"

    fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}
