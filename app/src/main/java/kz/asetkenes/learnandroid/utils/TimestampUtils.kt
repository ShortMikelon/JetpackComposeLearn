package kz.asetkenes.learnandroid.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
private const val DATE_FORMAT = "yyyy-MM-dd"
private const val TIME_FORMAT = "HH:mm"

fun convertStringToTimestamp(dateString: String): Long {
    val dateFormat = SimpleDateFormat(DATETIME_FORMAT, Locale.getDefault())
    val date = dateFormat.parse(dateString)
    return date?.time ?: 0
}

fun convertTimestampToString(timestamp: Long, onlyDate: Boolean = false): String {
    val format = when (onlyDate) {
        true -> DATE_FORMAT
        false -> "$DATE_FORMAT $TIME_FORMAT"
    }
    val dateFormat = SimpleDateFormat(format)
    val date = Date(timestamp)
    return dateFormat.format(date)
}