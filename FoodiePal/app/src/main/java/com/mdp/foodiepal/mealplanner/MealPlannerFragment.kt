package com.mdp.foodiepal.mealplanner

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
import com.mdp.foodiepal.databinding.FragmentMealPlannerBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MealPlannerFragment : Fragment() {

    private lateinit var binding: FragmentMealPlannerBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var context: Context

    private var selectedDate: String? = null
    private lateinit var mealPlannerAdapter: MealPlannerRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMealPlannerBinding.inflate(layoutInflater)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Update the selectedDate variable when the date changes
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            var dt = calendar.time

            // Format the date as needed
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            selectedDate = dateFormat.format(dt)

            loadData(sharedViewModel.getMealPlannerList())
        }

        loadData(sharedViewModel.getMealPlannerList())

        sharedViewModel.mealPlannerList.observe(viewLifecycleOwner, Observer { newData ->
            loadData(newData)
        })

        return binding.root
    }

    private fun loadData(list: ArrayList<MealPlanner>){
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var formattedDate = selectedDate ?: LocalDate.now().format(formatter)
        var data = list.filter { it.eventDate == formattedDate}.toCollection(ArrayList())
        mealPlannerAdapter = MealPlannerRecyclerViewAdapter(data)
        binding.rvMealPlanner.layoutManager = LinearLayoutManager(context)
        binding.rvMealPlanner.adapter = mealPlannerAdapter
    }
}