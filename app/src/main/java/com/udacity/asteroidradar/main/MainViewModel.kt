package com.udacity.asteroidradar.main

import android.app.Application
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
            val pictureOfDayFromInternet =
                AsteroidRadarApi.retrofitService.getPictureOfDay(BuildConfig.NASA_API_KEY).await()
                    .asDomainModel()
            if (pictureOfDayFromInternet.mediaType == "image") {
                _pictureOfDay.value = pictureOfDayFromInternet
            }
        }
    }

    fun displayAsteroidDetails(nearEarthObject: NearEarthObject) {
        _navigateToSelectedAsteroid.value = nearEarthObject
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
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