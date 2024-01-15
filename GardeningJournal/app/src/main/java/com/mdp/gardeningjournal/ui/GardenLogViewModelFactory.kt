package com.mdp.gardeningjournal.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GardenLogViewModelFactory(private val application: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenLogViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
