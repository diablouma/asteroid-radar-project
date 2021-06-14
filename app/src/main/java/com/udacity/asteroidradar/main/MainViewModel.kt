package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.NearEarthObject
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.network.asDomainModel
import com.udacity.asteroidradar.repository.NearEarthObjectRepository
import kotlinx.coroutines.*


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToSelectedAsteroid = MutableLiveData<NearEarthObject>()
    val navigateToSelectedAsteroid: LiveData<NearEarthObject>
        get() = _navigateToSelectedAsteroid

    private val database = AsteroidRadarDatabase.getInstance(application)
    private val nearEarthObjectRepository = NearEarthObjectRepository(database)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        viewModelScope.launch {
            nearEarthObjectRepository.refreshNearEarthObjects()
            getPictureOfDay()
        }
    }

    fun displayAsteroidDetails(nearEarthObject: NearEarthObject) {
        _navigateToSelectedAsteroid.value = nearEarthObject
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    private fun getPictureOfDay() {
        try {
            val pictureOfDayFromInternet =
                AsteroidRadarApi.retrofitService.getPictureOfDay(BuildConfig.NASA_API_KEY)
                    .asDomainModel()
            if (pictureOfDayFromInternet.mediaType == "image") {
                _pictureOfDay.value = pictureOfDayFromInternet
            }
        } catch (exception: Exception) {
            Log.i("MainViewModel", "No Internet connection available, not loading picture of day")
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
