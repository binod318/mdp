package com.mdp.foodiepal.mealplanner

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.mdp.foodiepal.R
import com.mdp.foodiepal.helper.JsonHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MealPlannerDialog(context: Context, private val finishDialog: () -> Unit) : AlertDialog(context) {
    private val calendar: Calendar = Calendar.getInstance()
    private var mealPlanners: MutableList<MealPlanner> = mutableListOf()
    private val jsonHelper = JsonHelper(context)

    init {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.meal_planner_dialog, null)

        handleDateSelect(view)

        handleTimeSelect(view)

        setView(view)

        setTitle("Add new meal plan")

        val blogsString = jsonHelper.readJsonFromFile("meal_planners.json")
        if(blogsString.isNotBlank())
            mealPlanners = jsonHelper.FromJson(blogsString, Array<MealPlanner>::class.java).toMutableList()

        setButton(BUTTON_POSITIVE, "Add") { _, _ ->
            val agenda = view.findViewById<EditText>(R.id.etAgenda).text.toString()
            val location = view.findViewById<EditText>(R.id.etLocation).text.toString()
            val date = view.findViewById<TextView>(R.id.tvSelectedDate).text.toString()
            val time = view.findViewById<TextView>(R.id.tvSelectedTime).text.toString()

            if(agenda.isNotBlank() && location.isNotBlank() && date.isNotBlank() && time.isNotBlank()){

                val mealPlan = MealPlanner(agenda, location, date, time)
                mealPlanners.add(mealPlan)
                val jsonString = jsonHelper.ToJson(mealPlanners)

                jsonHelper.writeJsonToFile("meal_planners.json", jsonString)

                dismiss()
            }

            dismiss()
            finishDialog()
        }

        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun handleDateSelect(view: View){

        val btnSelectDate = view.findViewById<Button>(R.id.btnSelectDate)
        val tvSelectedDate = view.findViewById<TextView>(R.id.tvSelectedDate)

        btnSelectDate.setOnClickListener{
            val inflater = LayoutInflater.from(context)
            val datePickerView = inflater.inflate(R.layout.date_picker_layout, null)

            val datePicker = datePickerView.findViewById<DatePicker>(R.id.datePicker)
            datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null
            )

            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setView(datePickerView)
            alertDialogBuilder.setTitle("Select Date")
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                calendar.set(Calendar.YEAR, datePicker.year)
                calendar.set(Calendar.MONTH, datePicker.month)
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val selectedDate = dateFormat.format(calendar.time)


                tvSelectedDate.text = selectedDate

                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }


    private fun handleTimeSelect(view: View){
        val btnSelectTime = view.findViewById<Button>(R.id.btnSelectTime)
        val tvSelectedTime = view.findViewById<TextView>(R.id.tvSelectedTime)

        btnSelectTime.setOnClickListener{
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val selectedTime = timeFormat.format(calendar.time)
                    tvSelectedTime.text = selectedTime
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }
    }
}