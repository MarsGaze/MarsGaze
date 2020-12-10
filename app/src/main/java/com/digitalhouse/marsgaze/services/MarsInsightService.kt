package com.digitalhouse.marsgaze.services

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface MarsInsightService {
    @GET("insight_weather/?api_key=0icv2askrRcamyOHi2h9LGtYi04Yhr8evRQ8crpV&feedtype=json&ver=1.0")
    fun getWeather(): Call<String>
}

// Utilizado o Scalars e o client para que retorne a string Json e nao um obejto para que possamos manipula-lo
// talvez tenha q criar um service separado pois estava dando erro quando eu colocava o GSONFactory
private val client = OkHttpClient.Builder().build()

val retrofit = Retrofit.Builder().baseUrl("https://api.nasa.gov/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(client)
    .build()

val serviceWeather: MarsInsightService = retrofit.create(MarsInsightService::class.java)