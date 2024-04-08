package com.example.projeto07

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiUsuarios {

    @GET("/usuarios")
    fun get(): Call<List<Usuario>>

    @POST("/usuarios")
    fun post(@Body usuario:Usuario): Call<Usuario>

}