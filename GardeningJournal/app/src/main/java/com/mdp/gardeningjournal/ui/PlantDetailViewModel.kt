package com.mdp.gardeningjournal.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdp.gardeningjournal.db.Plant

class PlantDetailViewModel: ViewModel() {
    private var _plant = MutableLiveData<Plant?>()
    val plant: Plant?
        get() = _plant.value

    fun update(p: Plant?){
        _plant.postValue(p!!)
    }

    fun resetPlant(){
        _plant = MutableLiveData<Plant?>()
    }
}