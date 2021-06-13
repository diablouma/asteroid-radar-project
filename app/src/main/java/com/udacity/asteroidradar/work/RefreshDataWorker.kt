package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.repository.NearEarthObjectRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORKER_NAME = "RefreshAsteroidsDBWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidRadarDatabase.getInstance(applicationContext)
        val nearEarthObjectRepository = NearEarthObjectRepository(database)
        nearEarthObjectRepository.refreshNearEarthObjects()

        return try {
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }

}