package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier

@JsonClass(generateAdapter = true)
data class NetworkNearEarthObjectsRepresentation(
    @Json(name="near_earth_objects")
    val nearEarthObjects: Map<String, List<NetworkNearEarthObject>>
)

@JsonClass(generateAdapter = true)
data class NetworkNearEarthObject(
    val id: String,
    @Json(name="absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @Json(name="estimated_diameter")
    val estimatedDiameter: NetworkEstimatedDiameter,
    @Json(name="is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,
    @Json(name="close_approach_data")
    val closeApproachData: List<NetworkCloseApproachData>
)

data class NetworkEstimatedDiameter(
    val kilometers: Kilometers
)

data class Kilometers(
    @Json(name="estimated_diameter_min")
    val estimatedDiameterMin: String,
    @Json(name="estimated_diameter_max")
    val estimatedDiameterMax: String
)

data class NetworkCloseApproachData(
    @Json(name="relative_velocity")
    val relativeVelocity: NetworkRelatedVelocity,
    @Json(name="miss_distance")
    val missDistance: NetworkMissDistance
)

data class NetworkRelatedVelocity(
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String
)

data class NetworkMissDistance(
    val astronomical: String
)