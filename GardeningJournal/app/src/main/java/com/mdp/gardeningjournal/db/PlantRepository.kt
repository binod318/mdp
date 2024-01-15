package com.mdp.gardeningjournal.db

import android.content.Context
import androidx.lifecycle.LiveData

class PlantRepository(application: Context) {
    private val plantDao: PlantDao

    init {
        val database = GardeningDatabase(application)
        plantDao = database.getPlantDao()
    }

    fun getAll() : LiveData<List<Plant>> {
        return plantDao.getAllPlants()
    }

    suspend fun insert(plant: Plant) {
        plantDao.addPlant(plant)
    }

    suspend fun update(plant: Plant) {
        plantDao.updatePlant(plant)
    }

    suspend fun delete(plant: Plant) {
        plantDao.deletePlant(plant)
    }

    fun getPlantById(plantId: Int): LiveData<Plant> {
        return plantDao.getPlantById(plantId)
    }
}