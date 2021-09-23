package com.example.myapplication.retrofit

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


class RetrofitInstance {


    companion object {

        private var retrofit: Retrofit? = null
        private val BASE_URL = "http://192.168.1.7:3000" // this IP is changed for your IP

        fun getInstance(): Retrofit? {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}