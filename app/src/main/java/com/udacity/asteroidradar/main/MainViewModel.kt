package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.asDomainModel
import com.udacity.asteroidradar.repository.NearEarthObjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidRadarDatabase.getInstance(application)
    private val nearEarthObjectRepository = NearEarthObjectRepository(database)

    init {
        viewModelScope.launch {
            nearEarthObjectRepository.refreshNearEarthObjects()
        }
    }
}