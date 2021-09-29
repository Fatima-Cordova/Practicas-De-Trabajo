package com.example.hora.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Time {
    private var time: DateFormat = SimpleDateFormat("HH:mm")

    fun dateTime(value: Date?): String? {
        return if (value == null) null else time.format(value)
    }
}