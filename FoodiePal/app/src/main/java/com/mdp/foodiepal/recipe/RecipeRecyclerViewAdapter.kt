package com.mdp.foodiepal.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mdp.foodiepal.R

class RecipeRecyclerViewAdapter(private var rList: ArrayList<Recipe>) : RecyclerView.Adapter<RecipeRecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            textTitle.text = rList[position].name
            textCookingTime.text = rList[position].cookingTime + " min"
            textIngredients.text = rList[position].ingredients
            textInstruction.text = rList[position].instruction
            imageThumbnail.setImageResource(rList[position].image)
            ratingUser.rating = rList[position].rating
        }
    }

    override fun getItemCount(): Int = rList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textTitle: TextView
        var textCookingTime: TextView
        var textIngredients: TextView
        var textInstruction: TextView
        var ratingUser: RatingBar
        var imageThumbnail: ImageView
        init{
            textTitle = itemView.findViewById(R.id.tvTitle) as TextView
            textCookingTime = itemView.findViewById(R.id.tvCookingTime) as TextView
            textIngredients = itemView.findViewById(R.id.tvIngredients) as TextView
            textInstruction = itemView.findViewById(R.id.tvInstruction) as TextView

            ratingUser = itemView.findViewById(R.id.rvRating) as RatingBar
            imageThumbnail = itemView.findViewById(R.id.ivThumbnail) as ImageView
        }
    }
}