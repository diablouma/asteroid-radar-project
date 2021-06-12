package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate
import com.udacity.asteroidradar.domain.NearEarthObject as DomainNearEarthObject

@Entity
data class NearEarthObject constructor (
    @PrimaryKey
    val id: String,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocityInKmPerSecond: String,
    val missDistance: String,
    val asOfDate: LocalDate
)
