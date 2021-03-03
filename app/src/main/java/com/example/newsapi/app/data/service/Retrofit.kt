package com.example.newsapi.app.data.service

import com.example.newsapi.app.data.remote.Endpoint
import com.patricio.dutra.desafiojeitto.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URLSERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val endpoint: Endpoint = getRetrofitInstance().create(Endpoint::class.java)
}