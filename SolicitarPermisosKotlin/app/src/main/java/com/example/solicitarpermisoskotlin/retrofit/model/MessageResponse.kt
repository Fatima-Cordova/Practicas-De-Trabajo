package com.example.solicitarpermisoskotlin.retrofit.model

data class MessageResponse (
    var userId: Int = 0,
    var id: Int = 0,
    var title: String = "",
    var completed: Boolean = false
    ) {
}