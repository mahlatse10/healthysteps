package com.example.healthysteps.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [HabitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HealthyStepsDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {

        @Volatile
        private var INSTANCE: HealthyStepsDatabase? = null

        fun getDatabase(context: Context): HealthyStepsDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthyStepsDatabase::class.java,
                    "healthysteps_database_v3"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}