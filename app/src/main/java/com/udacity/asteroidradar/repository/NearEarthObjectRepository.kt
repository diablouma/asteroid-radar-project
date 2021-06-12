package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import com.udacity.asteroidradar.domain.NearEarthObject
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class NearEarthObjectRepository(private val asteroidRadarDatabase: AsteroidRadarDatabase) {
    val nearEarthObjects: LiveData<List<NearEarthObject>> =
        Transformations.map(asteroidRadarDatabase.nearEarthObjectDao.getNearEarthObjects()) {
            it.asDomainModel()
        }

    suspend fun refreshNearEarthObjects() {
        withContext(Dispatchers.IO) {
            val nearEarthObjectsFromNetwork = AsteroidRadarApi.retrofitService.getNearEarthObjects(
                toStringRepresentation(LocalDate.now()),
                toStringRepresentation(LocalDate.now().plusDays(Constants.DEFAULT_END_DATE_DAYS)),
                BuildConfig.NASA_API_KEY
            ).await()
            asteroidRadarDatabase.nearEarthObjectDao.insertAll(*nearEarthObjectsFromNetwork.asDatabaseModel())
        }
    }
}