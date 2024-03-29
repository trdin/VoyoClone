package com.example.voyoclone.api

import com.example.voyoclone.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/graphql/")
    fun getData(@Query("query") query: String): Call<ApiResponse>
}