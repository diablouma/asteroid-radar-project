package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class AsteroidFilter { WEEK, TODAY, BEFORE_TODAY }

interface NasaNearObjectsService {
    @GET("/neo/rest/v1/feed")
    fun getNearEarthObjects(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Deferred<NetworkNearEarthObjectsContainer>

    @GET("/planetary/apod")
    fun getPictureOfDay(): Deferred<PictureOfDay>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor { apiKeyInterceptor(it) }
            .build()
    )
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

object AsteroidRadarApi {
    val retrofitService: NasaNearObjectsService by lazy {
        retrofit.create(NasaNearObjectsService::class.java)
    }
}

fun apiKeyInterceptor(it: Interceptor.Chain): Response {
    val originalRequest = it.request()
    val originalHttpUrl = originalRequest.url()

    val newHttpUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
        .build()

    val newRequest = originalRequest.newBuilder()
        .url(newHttpUrl)
        .build()

    return it.proceed(newRequest)
}