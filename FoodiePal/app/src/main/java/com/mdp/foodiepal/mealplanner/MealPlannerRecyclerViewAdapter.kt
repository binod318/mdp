package com.mdp.foodiepal.mealplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mdp.foodiepal.R

class MealPlannerRecyclerViewAdapter(private var planList: ArrayList<MealPlanner>) : RecyclerView.Adapter<MealPlannerRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_planner_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return planList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply{
            textAgenda.text = planList[position].agenda
            textLocation.text = planList[position].location
            textDatetime.text = planList[position].eventDate + " " + planList[position].eventTIme
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textAgenda: TextView
        var textLocation: TextView
        var textDatetime: TextView
        init{
            textAgenda = itemView.findViewById(R.id.tvAgenda) as TextView
            textLocation= itemView.findViewById(R.id.tvLocation) as TextView
            textDatetime = itemView.findViewById(R.id.tvDateTime) as TextView
        }
    }
}