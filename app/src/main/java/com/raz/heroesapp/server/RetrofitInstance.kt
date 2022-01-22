package com.raz.heroesapp.server

import com.raz.heroesapp.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getServerRequests: ServerRequests by lazy {
        retrofit.create(ServerRequests::class.java)
    }
}