package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val nearEarthObjects = AsteroidRadarApi.retrofitService.getNearEarthObjects(
                    LocalDate.of(2021, 6, 12).format(DateTimeFormatter.ofPattern(
                        API_QUERY_DATE_FORMAT
                    )),
                    LocalDate.of(2021, 6, 13).format(DateTimeFormatter.ofPattern(
                        API_QUERY_DATE_FORMAT
                    )),
                    BuildConfig.NASA_API_KEY
                ).await()
                Log.i("Mainactivity", nearEarthObjects.toString())
                Log.i("Mainactivity", nearEarthObjects.networkNearEarthObjectsContainer.keys.size.toString())
                Log.i("Mainactivity", nearEarthObjects.asDomainModel().size.toString())
                Log.i("Mainactivity", nearEarthObjects.asDatabaseModel().size.toString())
            }
        }
    }
}