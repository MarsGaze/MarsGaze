package com.digitalhouse.marsgaze.services

import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HubbleService {

    @GET("search")
    suspend fun getHubbleImg(
        @Query("keywords") keywords: String = "mars hubble",
        @Query("media_type") media_type: String = "image"
    ): HubbleResponse

    companion object {
        private const val BASE_URL = "https://images-api.nasa.gov/"

        fun create(): HubbleService {

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
                .create(HubbleService::class.java)
        }
    }

}

//https://images-api.nasa.gov/
// search?q=mars%20hubble%20space%20telescope
// &media_type=image