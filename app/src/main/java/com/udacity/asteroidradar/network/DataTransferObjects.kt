package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkNearEarthObjectsRepresentation(
    val nearEarthObjects: Map<String, List<NetworkNearEarthObject>>
)

@JsonClass(generateAdapter = true)
data class NetworkNearEarthObject(
    val id: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: NetworkEstimatedDiameter,
    val isPotentiallyHazardousAsteroid: Boolean,
    val closeApproachData: List<NetworkCloseApproachData>
)

data class NetworkEstimatedDiameter(
    val kilometers: Kilometers
)

data class Kilometers(
    val estimatedDiameterMin: String,
    val estimatedDiameterMax: String
)

data class NetworkCloseApproachData(
    val relativeVelocity: NetworkRelatedVelocity,
    val missDistance: NetworkMissDistance
)

data class NetworkRelatedVelocity(
    val kilometersPerSecond: String
)

data class NetworkMissDistance(
    val astronomical: String
)