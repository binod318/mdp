package com.mdp.gardeningjournal.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Insert
    suspend fun addPlant(plant: Plant)
    @Query("SELECT * FROM Plant ORDER By id desc")
    fun getAllPlants(): LiveData<List<Plant>>
    @Query("SELECT * FROM Plant where id = :itemId")
    fun getPlantById(itemId: Int): LiveData<Plant>
    @Update
    suspend fun updatePlant(plant: Plant)
    @Delete
    suspend fun deletePlant(plant: Plant)
    @Query("DELETE FROM Plant")
    suspend fun deleteAll()
}