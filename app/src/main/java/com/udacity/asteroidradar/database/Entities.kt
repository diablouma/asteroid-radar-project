package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NearEarthObject constructor (
    @PrimaryKey
    val id: String,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocityInKmPerSecond: String,
    val missDistance: String
)