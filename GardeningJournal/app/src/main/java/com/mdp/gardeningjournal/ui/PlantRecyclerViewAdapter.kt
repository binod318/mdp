package com.mdp.gardeningjournal.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mdp.gardeningjournal.R
import com.mdp.gardeningjournal.db.Plant

class PlantRecyclerViewAdapter(var context: Context, private var plantList: List<Plant>, private val onItemClick: (Plant) -> Unit, private val onLongClick: (Plant) -> Boolean)
    : RecyclerView.Adapter<PlantRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textName: TextView
        var textType: TextView
        var textFrequency: TextView
        var textPlantingDate: TextView
        init{
            textName = itemView.findViewById(R.id.tvName) as TextView
            textType= itemView.findViewById(R.id.tvType) as TextView
            textFrequency = itemView.findViewById(R.id.tvFrequency) as TextView
            textPlantingDate = itemView.findViewById(R.id.tvPlantingDate) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = plantList[position]
        holder.apply {
            textName.text = currentItem.name
            textType.text = currentItem.type
            textFrequency.text = currentItem.wateringFrequency.toString()
            textPlantingDate.text = currentItem.plantingDate
        }

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }

        holder.itemView.setOnLongClickListener{
            onLongClick(currentItem)
        }
    }
}