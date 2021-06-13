package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import com.udacity.asteroidradar.domain.NearEarthObject
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.asDomainModel
import com.udacity.asteroidradar.repository.NearEarthObjectRepository
import kotlinx.coroutines.*
import java.time.LocalDate

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidRadarDatabase.getInstance(application)
    private val nearEarthObjectRepository = NearEarthObjectRepository(database)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        viewModelScope.launch {
            nearEarthObjectRepository.refreshNearEarthObjects()
        }
    }

    val asteroids = nearEarthObjectRepository.nearEarthObjects

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}