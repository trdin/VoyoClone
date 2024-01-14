package com.example.voyoclone.api

import ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gql.voyo.si/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun fetchData(query: String, onResponse: (ApiResponse?) -> Unit, onFailure: (Throwable) -> Unit) {
        apiService.getData(query).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    onResponse(response.body())
                } else {
                    onFailure(Throwable("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle failure
                onFailure(t)
            }
        })
    }
}