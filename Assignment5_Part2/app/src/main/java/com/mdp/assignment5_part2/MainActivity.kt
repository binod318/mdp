package com.mdp.assignment5_part2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdp.assignment5_part2.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var cart = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()

        binding.btnViewCart.setOnClickListener{
            var msg = ""
            for (v in cart){
                msg += ", $v"
            }
            if(msg.isEmpty())
                msg = "No product added to cart!"

            Toast.makeText(applicationContext, msg.trim(','), Toast.LENGTH_LONG).show()
        }
    }

    private fun initList(){
        val items = ArrayList<Product>()
        items.add(Product("IPad", "IPad Pro 11 inch", "$400.0", R.drawable.ipad, R.drawable.apple_icon))
        items.add(Product("Macbook M3 Pro", "12-Core CPU \n 18-core GPU", "$2500.0", R.drawable.macbook, R.drawable.apple_icon))
        items.add(Product("Dell Inspiron", "13th Gen Intel Core i7", "$1499", R.drawable.dell_inspiron, R.drawable.dell_icon))
        items.add(Product("Logitech Keyboard", "Logitech - PRO X \n TKL LIGHTSPEED Wireless", "$199.0", R.drawable.logitech, R.drawable.logitech_icon))
        items.add(Product("Samsung Galaxy S23", "Samsung Galaxy Exynos 512 GB", "$1299.0", R.drawable.samsung, R.drawable.samsung_logo))

        val myAdapter = MyAdapter(this, items, cart)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = myAdapter
    }
}