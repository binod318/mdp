package com.mdp.foodiepal.blog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mdp.foodiepal.R

class BlogRecyclerViewAdapter(var context: Context, private var blogList: ArrayList<Blog>, private val onItemClick: (Blog) -> Unit)
    : RecyclerView.Adapter<BlogRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blogList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = blogList[position]
        var desc = currentItem.description
        desc =  if (desc.length > 25) { "${desc.substring(0, 25)}..." } else desc

        val drawable = ContextCompat.getDrawable(context, currentItem.thumbnail)
        if (drawable != null)
            holder.imageviewThumbanil.setImageDrawable(drawable)

        holder.apply{
            textTitle.text = currentItem.title
            textDesc.text = desc
            textAuthor.text = currentItem.author
            textPublishedOn.text = currentItem.publishedDate
        }

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textTitle: TextView
        var textDesc: TextView
        var textAuthor: TextView
        var textPublishedOn: TextView
        var imageviewThumbanil: ImageView
        init{
            textTitle = itemView.findViewById(R.id.tvTitle) as TextView
            textDesc= itemView.findViewById(R.id.tvDesc) as TextView
            textAuthor = itemView.findViewById(R.id.tvAuthor) as TextView
            textPublishedOn = itemView.findViewById(R.id.tvPublishedOn) as TextView
            imageviewThumbanil = itemView.findViewById(R.id.imgThumbnail) as ImageView
        }
    }
}