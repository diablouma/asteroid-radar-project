package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

interface NearEarthObjectDao {
    @Query("select * from nearEarthObject")
    fun getNearEarthObjects(): LiveData<List<NearEarthObject>>
}


@Database(entities = [NearEarthObject::class], version = 1, exportSchema = false)
abstract class AsteroidRadarDatabase : RoomDatabase() {
    abstract val nearEarthObjectDao: NearEarthObjectDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidRadarDatabase? = null

        fun getInstance(context: Context): AsteroidRadarDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRadarDatabase::class.java,
                        "asteroid_radar_database"
                    ).build()
                }

                INSTANCE = instance

                return instance
            }
        }

    }
}