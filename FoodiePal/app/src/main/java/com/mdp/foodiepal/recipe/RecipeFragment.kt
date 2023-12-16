package com.mdp.foodiepal.recipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdp.foodiepal.SharedViewModel
import com.mdp.foodiepal.databinding.FragmentRecipeBinding
import com.mdp.foodiepal.helper.JsonHelper

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private lateinit var jsonHelper: JsonHelper
    private lateinit var context: Context
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recipeAdapter: RecipeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext().applicationContext
        jsonHelper = JsonHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRecipeBinding.inflate(inflater, container, false)

        loadData(sharedViewModel.getRecipeList())

        sharedViewModel.recipeList.observe(viewLifecycleOwner, Observer { newData ->
            loadData(newData)
        })

        return binding.root
    }

    private fun loadData(list: ArrayList<Recipe>){
        recipeAdapter = RecipeRecyclerViewAdapter(list.toCollection(ArrayList()))
        binding.rvRecipes.layoutManager = LinearLayoutManager(context)
        binding.rvRecipes.adapter = recipeAdapter
    }
}