package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.assignment2.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var defaultFoodList = mutableListOf("Hamburger", "Pizza", "Mexican", "American", "Chinese")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{
            val newFood = binding.etAddNewFood.text.toString();
            defaultFoodList.add(newFood)
            binding.etAddNewFood.text.clear()
            binding.tvFood.text = newFood;
        }

        binding.btnDecide.setOnClickListener{
            // Generate a random index between 0 and the size of the list minus 1
            val randomIndex = Random.nextInt(0, defaultFoodList.size)
            val randomValue = defaultFoodList[randomIndex]

            binding.tvFood.text = randomValue;
        }

    }
}
