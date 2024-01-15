package com.mdp.gardeningjournal.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mdp.gardeningjournal.databinding.FragmentGardenLogBinding
import com.mdp.gardeningjournal.db.Plant

class GardenLogFragment : BaseFragment() {
    private lateinit var binding: FragmentGardenLogBinding
    private lateinit var viewModel: GardenLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenLogBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener{
            navigateToDetail(0)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, GardenLogViewModelFactory(requireContext()))[GardenLogViewModel::class.java]

        viewModel.allPlants.observe(viewLifecycleOwner, Observer {
             loadPlants(it)
        })

        // Set the title in the ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Garden Log"
    }

    private fun loadPlants(plants: List<Plant>){
        binding.rvPlants.setHasFixedSize(false)
        binding.rvPlants.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPlants.adapter = PlantRecyclerViewAdapter(requireContext(), plants,
            {selectedItem -> navigateToDetail(selectedItem.id)},
            {selectedItem -> handleLongPress(selectedItem)}
        )
    }

    private fun navigateToDetail(id: Int){
        val directions =GardenLogFragmentDirections.actionGardenLogToPlantDetails(id)
        findNavController().navigate(directions)
    }

    private fun handleLongPress(plant: Plant): Boolean{
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle("Delete Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to delete this plant?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            deletePlant(plant)
            context?.toast("Plant deleted successfully!")
        }

        alertDialogBuilder.setNegativeButton("No") { _, _ ->
            context?.toast("Delete operation canceled!")
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        return true
    }

    private fun deletePlant(plant: Plant){
        viewModel.delete(plant)
    }
}