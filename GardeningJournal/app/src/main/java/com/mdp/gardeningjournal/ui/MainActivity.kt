package com.mdp.gardeningjournal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mdp.gardeningjournal.R
import com.mdp.gardeningjournal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var plantDetailViewModel: PlantDetailViewModel
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interactViewModel()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        mNavController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, mNavController)
    }

    private fun interactViewModel(){
        plantDetailViewModel = ViewModelProvider(this)[PlantDetailViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        plantDetailViewModel.resetPlant()
        return mNavController.navigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        plantDetailViewModel.resetPlant()
    }
}
