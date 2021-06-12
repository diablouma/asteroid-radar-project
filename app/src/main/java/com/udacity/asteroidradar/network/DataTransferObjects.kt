package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import java.util.*

data class NetworkNearEarthObject(
    val date: Date,
    val id: String,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val velocityInKmsPerSecond: String,
    val missDistance: String
)