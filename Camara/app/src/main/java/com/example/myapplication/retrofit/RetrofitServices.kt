package com.example.myapplication.retrofit

import com.example.myapplication.retrofit.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST

import retrofit2.http.Multipart


interface RetrofitServices {

    @Multipart
    @POST("upload/")
    fun uploadAttachment(@Part filePart: MultipartBody.Part): Call<PhotoResponse>
}