package com.example.solicitarpermisoskotlin.retrofit

import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse
import com.example.solicitarpermisoskotlin.retrofit.model.PhotosResponse
import com.example.solicitarpermisoskotlin.retrofit.model.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {

    @GET("todos/")
    fun getListaMensajes(): Call<List<MessageResponse>>

    @GET("todos/{id}")
    fun getMensaje(@Path("id") idMensaje : String): Call<MessageResponse>

    @GET("users/")
    fun getUsers() : Call<List<UserResponse>>

    @GET("users/{id}")
    fun getUser(@Path("id") idUser : String) : Call<UserResponse>

    @GET("photos/")
    fun getPhotos() : Call<List<PhotosResponse>>

    @GET("photos/{id}")
    fun getPhoto(@Path("id") idPhoto : String) : Call<PhotosResponse>
}

