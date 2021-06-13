package com.udacity.asteroidradar.domain

import java.time.LocalDate

data class NearEarthObject (
    val id: String,
    val codeName: String,
    val asOfDate: LocalDate,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocityInKmPerSecond: String,
    val missDistance: String
)