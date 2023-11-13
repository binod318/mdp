package com.mdp.assignment5_part2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mdp.assignment5_part2.databinding.ActivityDetailBinding

class DetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("name") && intent.hasExtra("desc") && intent.hasExtra("price") && intent.hasExtra("image")){
            var name = intent.getStringExtra("name")
            var desc = intent.getStringExtra("desc")
            var price = intent.getStringExtra("price")
            var image = intent.getIntExtra("image", 0)

            binding.tvTitle.text = name.toString()
            binding.tvDesc.text = desc.toString()
            binding.tvPrice.text = price.toString()
            binding.ivThumbnail.setImageResource(image)
        }

        binding.btnHome.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }
}