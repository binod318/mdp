package com.mdp.foodiepal.blog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdp.foodiepal.R
import com.mdp.foodiepal.SharedViewModel
import com.mdp.foodiepal.databinding.FragmentBlogBinding
import com.mdp.foodiepal.helper.JsonHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BlogFragment : Fragment() {
    private lateinit var binding: FragmentBlogBinding;
    private lateinit var jsonHelper: JsonHelper
    private lateinit var context: Context
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext().applicationContext
        jsonHelper = JsonHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_blog, container, false)
        binding = FragmentBlogBinding.bind(view)

        loadData(sharedViewModel.getBlogList())

        sharedViewModel.blogList.observe(viewLifecycleOwner, Observer { newData ->
            loadData(newData)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        var data = jsonHelper.ReadFromJson("blogs.json", Array<Blog>::class.java)?.toCollection(
            java.util.ArrayList<Blog>()
        ) ?: java.util.ArrayList()
        loadData(data)
    }

    private fun navigateToBlog(blog: Blog){
        val intent = Intent(context, BlogActivity::class.java)
        intent.putExtra("title", blog.title)
        intent.putExtra("author", blog.author)
        intent.putExtra("description", blog.description)
        intent.putExtra("publishedDate", blog.publishedDate)
        intent.putExtra("thumbnail", blog.thumbnail)
        intent.putExtra("favorite", blog.favorite)
        startActivity(intent)
    }

    private fun loadData(list: ArrayList<Blog>){
        //Favourites
        val favorites = list.filter { it.favorite }.toCollection(ArrayList())
        val myAdapter = BlogRecyclerViewAdapter(context, favorites) { selectedItem ->
            navigateToBlog(selectedItem)
        }
        binding.rvFavourites.layoutManager = LinearLayoutManager(context)
        binding.rvFavourites.adapter = myAdapter

        //Latest
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val latest = list.sortedByDescending { LocalDateTime.parse(it.publishedDate, formatter) }.toCollection(ArrayList())
        val latestAdapter = BlogRecyclerViewAdapter(context, latest) { selectedItem ->
            navigateToBlog(selectedItem)
        }
        binding.rvLatest.layoutManager = LinearLayoutManager(context)
        binding.rvLatest.adapter = latestAdapter
    }
}