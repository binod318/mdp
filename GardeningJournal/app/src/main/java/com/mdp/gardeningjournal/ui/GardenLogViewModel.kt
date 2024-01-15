package com.mdp.gardeningjournal.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdp.gardeningjournal.db.Plant
import com.mdp.gardeningjournal.db.PlantRepository
import kotlinx.coroutines.launch

class GardenLogViewModel(application: Context): ViewModel() {
    private val repository: PlantRepository
    var allPlants: LiveData<List<Plant>>
    var plant: LiveData<Plant>

    init {
        repository = PlantRepository(application)
        allPlants = repository.getAll()
        plant = MutableLiveData()
    }

    fun insert(plant: Plant) = viewModelScope.launch {
        repository.insert(plant)
    }

    fun update(plant: Plant) = viewModelScope.launch {
        repository.update(plant)
    }

    fun delete(plant: Plant) = viewModelScope.launch {
        repository.delete(plant)
    }

    fun findPlantById(plantId: Int) = viewModelScope.launch {
        plant = repository.getPlantById(plantId)
    }
}