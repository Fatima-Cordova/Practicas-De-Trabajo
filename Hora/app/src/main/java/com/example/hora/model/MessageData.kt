package com.example.hora.model

import com.example.hora.util.Time
import java.util.*

data class MessageData(
    var messageT:String,
    var time:String,
){
    var createDate: Date? = Date()
}