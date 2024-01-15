package com.mdp.gardeningjournal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [Plant::class],
    version = 1
)
abstract class GardeningDatabase() : RoomDatabase() {
    abstract fun getPlantDao() : PlantDao

    companion object {
        @Volatile private var instance : GardeningDatabase? = null
        private val LOCK = Any()

        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GardeningDatabase::class.java,
            "gardeningdatabase"
        ).build()
    }
}