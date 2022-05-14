package com.egeysn.movies_sprint.utils

import android.text.TextUtils
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

fun String.toYear(): String {
    if (!TextUtils.isEmpty(this)) {
        val dt = DateTime(this, DateTimeZone.getDefault())
        return try {
            return dt.toString("yyyy")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    return ""
}
