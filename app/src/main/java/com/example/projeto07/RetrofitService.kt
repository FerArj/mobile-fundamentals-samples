package com.example.projeto07

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    const val BASE_URL = "https://5f861cfdc8a16a0016e6aacd.mockapi.io/sptech-api/"

    fun getApiUsuariosService(): ApiUsuarios {
        val cliente =
           Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
               .create(ApiUsuarios::class.java)

        return cliente
    }
}