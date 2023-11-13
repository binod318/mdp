package com.mdp.assignment5_part2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var context: Context, var eList: ArrayList<Product>, var cart: MutableList<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        holder.apply {
            title.text = eList[position].name
            desc.text = eList[position].desc
            price.text = eList[position].price
            thumbnail.setImageResource(eList[position].image)
            logo.setImageResource(eList[position].logo)
        }

        holder.mainLayout.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)

            val name = eList[position].name
            Toast.makeText(context, "$name clicked!", Toast.LENGTH_LONG).show()

            intent.putExtra("name", eList[position].name)
            intent.putExtra("desc", eList[position].desc)
            intent.putExtra("price", eList[position].price)
            intent.putExtra("image", eList[position].image)

            context.startActivity(intent)
        }

        holder.add.setOnClickListener{
            val product: String = eList[position].name
            cart.add(product)
            Toast.makeText(context, "$product added to cart!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return eList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title: TextView
        var desc:TextView
        var price:TextView
        var thumbnail: ImageView
        var logo: ImageView
        var mainLayout: LinearLayout
        var add: Button
        init{
            title = itemView.findViewById(R.id.tvTitle) as TextView
            desc = itemView.findViewById(R.id.tvDesc) as TextView
            price = itemView.findViewById(R.id.tvPrice) as TextView
            thumbnail = itemView.findViewById(R.id.ivThumbnail) as ImageView
            logo = itemView.findViewById(R.id.ivLogo) as ImageView
            mainLayout = itemView.findViewById(R.id.llMain) as LinearLayout
            add = itemView.findViewById(R.id.btnAdd) as Button
        }
    }
}