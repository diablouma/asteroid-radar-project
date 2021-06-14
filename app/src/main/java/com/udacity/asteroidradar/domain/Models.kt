package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class NearEarthObject(
    val id: String,
    val codeName: String,
    val asOfDate: LocalDate,
    val absoluteMagnitude: Double,
    val estimatedDiameterMax: Double,
    val isPotentiallyHazardousAsteroid: Boolean,
    val relativeVelocityInKmPerSecond: String,
    val missDistance: String
) : Parcelable {
    val dateAsString = toStringRepresentation(asOfDate)
}

data class PictureOfDay(
    val mediaType: String,
    val title: String,
    val url: String
)