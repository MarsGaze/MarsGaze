package com.digitalhouse.marsgaze.services

import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "aYqv4pRlxcPJ2jcv0E26dh8c1VFgF5FDIRKnMbwg"

/**
 * Used to connect to the Mars Rover Photos API
 */
interface MarsRoversPhotosService {

    /**
     * We need a different endpoint to fetch the latest photos from a rover.
     */
    @GET("{rover}/latest_photos")
    suspend fun getLatestPhotos(
        @Path("rover") rover: String,
        @Query("api_key") api_key: String = API_KEY
    ): RoverResponse

    /**
     * Fetches photos from a single rover queried by martian sol
     */
    @GET("{rover}/photos")
    suspend fun getPhotos(
        @Path("rover") rover: String,
        @Query("sol") sol: Int,
        @Query("api_key") api_key: String = API_KEY,
    ): RoverResponse

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"

        fun create(): MarsRoversPhotosService {

            /**
             * Basic OkHttp interceptor which logs request and response data
             * https://square.github.io/okhttp/interceptors/
             *
             */
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }


            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            // Default
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarsRoversPhotosService::class.java)
        }
    }

}