package com.udacity.asteroidradar.domain

import java.time.LocalDate

data class NearEarthObject(
    val id: String,
    val codeName: String,
    val asOfDate: LocalDate,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocityInKmPerSecond: String,
    val missDistance: String
)

data class PictureOfDay(
    val mediaType: String,
    val title: String,
    val url: String
)