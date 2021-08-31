package com.example.solicitarpermisoskotlin.retrofit

import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part


interface RetrofitServices {

    @GET("todos/")
    fun getListaMensajes(): Call<List<MessageResponse>>
}

