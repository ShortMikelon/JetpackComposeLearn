package kz.asetkenes.learnandroid.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertStringToTimestamp(dateString: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(dateString)
    return date?.time ?: 0
}

fun convertTimestampToString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(timestamp)
        return dateFormat.format(date)
}