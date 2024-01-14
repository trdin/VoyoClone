package com.example.voyoclone.api

import ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class ApiManager {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gql.voyo.si/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun fetchData(query: String, onResponse: (ApiResponse?) -> Unit, onFailure: (Throwable?, Int?) -> Unit) {
        apiService.getData(query).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    var body = response.body()
                    //check for errors that were sent form the API
                    if(body?.errors != null){
                        val firstErrorMessage = body.errors?.firstOrNull()?.message
                        val statusCode = firstErrorMessage?.status ?: response.code()
                        onFailure(null, statusCode)
                        Timber.tag("Api Error").d("Error in response; CODE: $statusCode, MESSAGE: $firstErrorMessage")
                    }else {
                        onResponse(response.body())
                    }
                } else {
                    Timber.tag("Api Error").d("Response not succesfull; CODE: ${response.code()}")
                    onFailure(null, response.code())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle failure
                Timber.tag("Api Error").d(t.cause)
                onFailure(t, null)
            }
        })
    }
}