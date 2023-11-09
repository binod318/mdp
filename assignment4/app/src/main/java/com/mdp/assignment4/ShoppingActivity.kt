package com.mdp.assignment4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mdp.assignment4.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding
    private var userName: String = ""
    companion object {
        private const val USERNAME = "USERNAME"
        fun createIntent(context: Context, userName: String): Intent {
            return Intent(context, ShoppingActivity::class.java).apply {
                putExtra(USERNAME, userName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getArguments()
        bindViews()
    }

    private fun bindViews() {
        binding.tvWelcome.text = getString(R.string.welcome, userName)
        binding.gvShopCategories.adapter = ShoppingGridViewAdapter(this, getShoppingItems())
    }

    private fun getArguments() {
        userName = intent.getStringExtra(USERNAME) ?: ""
    }

    private fun getShoppingItems(): List<ShoppingGridItem> {
        return listOf(
            ShoppingGridItem("Electronics", R.drawable.electronics),
            ShoppingGridItem("Clothing", R.drawable.clothing),
            ShoppingGridItem("Beauty", R.drawable.beauty),
            ShoppingGridItem("Food", R.drawable.food),
        )
    }
}