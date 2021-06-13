package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.dateUtils.localDateFrom
import com.udacity.asteroidradar.domain.NearEarthObject
import com.udacity.asteroidradar.database.NearEarthObject as DatabaseNearEarthObject

@JsonClass(generateAdapter = true)
data class NetworkNearEarthObjectsContainer(
    @Json(name = "near_earth_objects")
    val networkNearEarthObjectsContainer: Map<String, List<NetworkNearEarthObject>>
)

@JsonClass(generateAdapter = true)
data class NetworkNearEarthObject(
    val id: String,
    @Json(name = "name")
    val codeName: String,
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: NetworkEstimatedDiameter,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,
    @Json(name = "close_approach_data")
    val closeApproachData: List<NetworkCloseApproachData>
)

data class NetworkEstimatedDiameter(
    val kilometers: Kilometers
)

data class Kilometers(
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: String,
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double
)

data class NetworkCloseApproachData(
    @Json(name = "relative_velocity")
    val relativeVelocity: NetworkRelatedVelocity,
    @Json(name = "miss_distance")
    val missDistance: NetworkMissDistance
)

data class NetworkRelatedVelocity(
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String
)

data class NetworkMissDistance(
    val astronomical: String
)

fun NetworkNearEarthObjectsContainer.asDomainModel(): List<NearEarthObject> {
    val result = ArrayList<NearEarthObject>()
    this.networkNearEarthObjectsContainer.map {
        it.value.forEach { networkNearEarthObject ->
            result.add(
                NearEarthObject(
                    networkNearEarthObject.id,
                    networkNearEarthObject.codeName,
                    localDateFrom(it.key),
                    networkNearEarthObject.absoluteMagnitude,
                    networkNearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMax,
                    networkNearEarthObject.isPotentiallyHazardousAsteroid,
                    networkNearEarthObject.closeApproachData[0].relativeVelocity.kilometersPerSecond,
                    networkNearEarthObject.closeApproachData[0].missDistance.astronomical
                )
            )
        }
    }
    return result
}

fun NetworkNearEarthObjectsContainer.asDatabaseModel(): Array<DatabaseNearEarthObject> {
    val result = ArrayList<DatabaseNearEarthObject>()
    this.networkNearEarthObjectsContainer.map {
        it.value.forEach { networkNearEarthObject ->
            result.add(
                DatabaseNearEarthObject(
                    networkNearEarthObject.id,
                    networkNearEarthObject.codeName,
                    networkNearEarthObject.absoluteMagnitude,
                    networkNearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMax,
                    networkNearEarthObject.isPotentiallyHazardousAsteroid,
                    networkNearEarthObject.closeApproachData[0].relativeVelocity.kilometersPerSecond,
                    networkNearEarthObject.closeApproachData[0].missDistance.astronomical,
                    localDateFrom(it.key)
                )
            )
        }
    }
    return result.toTypedArray()
}