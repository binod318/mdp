package com.mdp.foodiepal.blog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mdp.foodiepal.R
import com.mdp.foodiepal.databinding.ActivityBlogBinding
import com.mdp.foodiepal.helper.JsonHelper


class BlogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlogBinding
    private val jsonHelper = JsonHelper(this)
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val description = intent.getStringExtra("description")
        val publishedDate = intent.getStringExtra("publishedDate")
        val thumbnail = intent.getIntExtra("thumbnail", 0)
        val fav = intent.getBooleanExtra("favorite", false)

        binding.tvTitle.text = title
        binding.tvAuthor.text = author
        binding.tvDesc.text = description
        binding.tvPublishedDate.text = publishedDate
        binding.imgThumbnail.setImageResource(thumbnail)

        binding.ivFavorite.tag = fav
        binding.ivFavorite.setImageResource(if(fav) R.drawable.favorite else R.drawable.favorite_border)

        binding.ivFavorite.setOnClickListener{it: View ->
            var blogsString = jsonHelper.readJsonFromFile("blogs.json")
            if(blogsString.isNotBlank()){
                var blogs = gson.fromJson(blogsString, Array<Blog>::class.java).toMutableList()
                blogs = blogs.map{ item -> item.copy(favorite = if(item.title == title) !item.favorite else item.favorite)}.toMutableList()
                blogsString = gson.toJson(blogs)
                jsonHelper.writeJsonToFile("blogs.json", blogsString)
            }

            val tag = it.tag as Boolean
            it.tag = !tag
            binding.ivFavorite.setImageResource(if(!tag) R.drawable.favorite else R.drawable.favorite_border)
            Toast.makeText(this, if(!tag) "Blog added to favorite" else "Blog removed from favorite", Toast.LENGTH_SHORT).show()
        }

        binding.ivShare.setOnClickListener{
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hello, Please check my new blog with title: $title"
            )

            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")

            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            } else {
                // If WhatsApp is not installed, you may want to handle this case
                Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}