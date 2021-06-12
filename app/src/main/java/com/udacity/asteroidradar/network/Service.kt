package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NasaNearObjectsService {
    @GET
    fun getNearEarthObjects(
        @Query("start_date") startDate: Date,
        @Query("end_date") endDate: Date,
        @Query("api_key") apiKey: String
    ): Deferred<NetworkNearEarthObject>
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.nasa.gov/neo/rest/v1/feed")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

object AsteroidRadarApi {
    val retrofitService: NasaNearObjectsService by lazy {
        retrofit.create(NasaNearObjectsService::class.java)
    }
}