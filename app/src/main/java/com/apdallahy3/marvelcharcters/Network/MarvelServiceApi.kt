package com.apdallahy3.marvelcharcters.Network

import android.util.Log
import com.apdallahy3.marvelcharcters.Network.Models.CharacterProperty
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor()
        .apply
        { HttpLoggingInterceptor.Level.BODY })
         .readTimeout(60, TimeUnit.SECONDS)
         .connectTimeout(60, TimeUnit.SECONDS)


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client.build())
    .build()

interface MarvelServiceApi {

    @GET("characters")
    fun getCharacters(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String
    ):
            Deferred<List<CharacterProperty>>
}

object MarvelAPI {

    val retrofitService: MarvelServiceApi by lazy {
        retrofit.create(MarvelServiceApi::class.java)
    }

}