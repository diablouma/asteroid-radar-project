package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import com.udacity.asteroidradar.domain.NearEarthObject
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidFilter
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.network.asDomainModel
import com.udacity.asteroidradar.repository.NearEarthObjectRepository
import kotlinx.coroutines.*
import java.net.InetAddress
import java.time.LocalDate


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
            if (isInternetAvailable()) {
                val pictureOfDayFromInternet =
                    AsteroidRadarApi.retrofitService.getPictureOfDay()
                        .await()
                        .asDomainModel()
                if (pictureOfDayFromInternet.mediaType == "image") {
                    _pictureOfDay.value = pictureOfDayFromInternet
                }
            } else {
                Log.i(
                    "MainViewModel",
                    "No Internet connection available, not loading picture of day"
                )
            }
        }
    }

    fun displayAsteroidDetails(nearEarthObject: NearEarthObject) {
        _navigateToSelectedAsteroid.value = nearEarthObject
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    var asteroids = nearEarthObjectRepository.asteroidsForNextWeek

    private fun filterAsteroids(filter: AsteroidFilter) {
        asteroids = when (filter) {
            AsteroidFilter.TODAY -> {
                nearEarthObjectRepository.asteroidsForToday
            }
            AsteroidFilter.BEFORE_TODAY -> {
                nearEarthObjectRepository.asteroidsBeforeToday
            }
            else -> {
                nearEarthObjectRepository.asteroidsForNextWeek
            }
        }
    }

    fun updateFilter(filter: AsteroidFilter) {
        filterAsteroids(filter)
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val ipAddr: InetAddress = InetAddress.getByName("www.google.com")
                return@withContext !ipAddr.equals("")
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }
}


