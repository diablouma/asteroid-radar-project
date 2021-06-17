package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate

@Dao
interface NearEarthObjectDao {
    @Query("select * from nearEarthObject where asOfDate >= (:now) order by asOfDate DESC")
    fun getAsteroidsForNextWeek(now: LocalDate = LocalDate.now()): LiveData<List<NearEarthObject>>

    @Query("select * from nearEarthObject where asOfDate = (:now) order by asOfDate DESC")
    fun getTodayAsteroids(now: LocalDate = LocalDate.now()): LiveData<List<NearEarthObject>>

    @Query("select * from nearEarthObject where asOfDate < (:now) order by asOfDate DESC")
    fun getBeforeTodayAsteroids(now: LocalDate = LocalDate.now()): LiveData<List<NearEarthObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: NearEarthObject)
}


@Database(entities = [NearEarthObject::class], version = 2, exportSchema = false)
@TypeConverters(LocalDateConverters::class)
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
                        "asteroid_radar_database",
                    ).fallbackToDestructiveMigration().build()
                }

                INSTANCE = instance

                return instance
            }
        }

    }
}