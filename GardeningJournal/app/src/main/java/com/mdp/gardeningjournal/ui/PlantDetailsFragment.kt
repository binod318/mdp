package com.mdp.gardeningjournal.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mdp.gardeningjournal.R
import com.mdp.gardeningjournal.databinding.FragmentPlantDetailsBinding
import com.mdp.gardeningjournal.db.Plant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PlantDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentPlantDetailsBinding
    private val calendar: Calendar = Calendar.getInstance()
    private val args: PlantDetailsFragmentArgs by navArgs()
    private lateinit var gardenLogViewModel: GardenLogViewModel
    private val viewModel: PlantDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetailsBinding.inflate(inflater, container, false)

        trackTextChanged(binding.etName)
        trackTextChanged(binding.etType)
        trackTextChanged(binding.etFrequency)

        binding.etFrequency.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                val freqText = binding.etFrequency
                if(freqText.text.isNotEmpty() && !freqText.text.toString().contains("days")){
                    freqText.setText(freqText.text.toString() + " days")
                }
            }
        }

        binding.btnSelectDate.setOnClickListener{
            binding.etFrequency.clearFocus()
            val inflater = LayoutInflater.from(requireContext())
            val datePickerView = inflater.inflate(R.layout.date_picker_layout, null)

            val datePicker = datePickerView.findViewById<DatePicker>(R.id.datePicker)
            datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null
            )

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setView(datePickerView)
            alertDialogBuilder.setTitle("Select Planting Date")
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                calendar.set(Calendar.YEAR, datePicker.year)
                calendar.set(Calendar.MONTH, datePicker.month)
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val selectedDate = dateFormat.format(calendar.time)
                binding.tvPlantingDate.text = selectedDate

                updatePlant()

                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        handleSave()

        return binding.root
    }

    private fun trackTextChanged(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updatePlant()
            }
        })
    }

    private fun updatePlant(){
        val plant = viewModel.plant
        if(plant != null){
            plant.name = binding.etName?.text.toString()
            plant.type = binding.etType?.text.toString()
            if(binding.tvPlantingDate?.text?.isNotEmpty() == true)
                plant.plantingDate = binding.tvPlantingDate?.text.toString()

            if(binding.etFrequency?.text?.isNotBlank() == true) {
                val arr = binding.etFrequency?.text.toString().split(" ").toTypedArray()
                if(arr.size == 2 && arr[0].isNotBlank()){
                    val freq = arr[0].toInt()
                    plant.wateringFrequency = freq
                } else
                    plant.wateringFrequency = 0
            }

            viewModel.update(plant)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gardenLogViewModel = ViewModelProvider(this, GardenLogViewModelFactory(requireContext()))[GardenLogViewModel::class.java]

        var t = viewModel.plant
        displayPlant(viewModel.plant)

        if(args?.plantid!! > 0){
            gardenLogViewModel.findPlantById(args.plantid)
            binding.btnSave.text = "Update"
        } else {
            binding.btnSave.text = "Create"
        }

        gardenLogViewModel?.plant?.observe(viewLifecycleOwner, Observer {
            if(viewModel.plant?.id != it.id){
                viewModel.update(it)
                displayPlant(it)
            }
        })

        // Set the title in the ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Plant Detail"
    }

    private fun displayPlant(p: Plant?){
        if(p != null){
            var freq = p!!.wateringFrequency.toString()
            if(!freq.contains("days"))
                freq = "$freq days"
            binding.apply {
                etName.setText(p!!.name)
                etType.setText(p!!.type)
                etFrequency.setText(freq)
                tvPlantingDate.text = p!!.plantingDate
            }
        }
    }

    private fun handleSave(){

        binding.apply {
            btnSave.setOnClickListener { it ->
                var plant = viewModel.plant
                if(etName.text.isNullOrEmpty()){
                    etName.error = "Plant name is required!"
                    etName.requestFocus()
                    return@setOnClickListener // stop further process
                }
                if(etType.text.isNullOrEmpty()){
                    etType.error = "Plant type is required!"
                    etType.requestFocus()
                    return@setOnClickListener // stop further process
                }
                if(etFrequency.text.isNullOrEmpty()){
                    etFrequency.error = "Planting frequency is required!"
                    etFrequency.requestFocus()
                    return@setOnClickListener // stop further process
                }
                if(tvPlantingDate.text.isNullOrEmpty()){
                    tvPlantingDate.error = "Planting Date required!"
                    return@setOnClickListener // stop further process
                }

                val freq = (etFrequency.text.toString().split(" ").toTypedArray())[0].toInt()

                if(plant == null){
                    plant = Plant(etName.text.toString(), etType.text.toString(), freq, tvPlantingDate.text.toString())
                    gardenLogViewModel.insert(plant!!)
                    viewModel.resetPlant()
                    context?.toast("Plant created!")
                }
                else {
                    plant?.apply {
                        name = etName.text.toString()
                        type = etType.text.toString()
                        wateringFrequency = freq
                        plantingDate = tvPlantingDate.text.toString()
                    }

                    gardenLogViewModel.update(plant)
                    viewModel.resetPlant()
                    context?.toast("Plant updated!")
                }

                val action = PlantDetailsFragmentDirections.actionPlantDetailsToGardenLog()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}