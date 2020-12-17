package com.digitalhouse.marsgaze.services

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "aYqv4pRlxcPJ2jcv0E26dh8c1VFgF5FDIRKnMbwg"

interface InsightService {

    @GET("insight_weather/")
    suspend fun getInsightResponse(
        @Query("api_key") api_key: String = API_KEY,
        @Query("feedtype") feedtype: String = "json",
        @Query("ver") media_type: String = "1.0"
    ): Response<JsonObject>

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"

        fun create(): InsightService {

            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            // Default
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(InsightService::class.java)
        }
    }

}