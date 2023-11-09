package com.mdp.assignment4

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import com.mdp.assignment4.databinding.LayoutItemGridBinding


class ShoppingGridViewAdapter(private val context: Context, private val list: List<ShoppingGridItem>) : ArrayAdapter<ShoppingGridItem>(context, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if(view == null) {
            val binding = LayoutItemGridBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater, parent, false)
            binding.tvCategory.text = list[position].name
            binding.ivCategory.setImageDrawable(
                AppCompatResources.getDrawable(context, list[position].categoryIcon)
            )
            view = binding.root
        }

        view.setOnClickListener {
            view.context.showToast("You have chosen ${list[position].name} category of shopping")
        }

        return view
    }

}