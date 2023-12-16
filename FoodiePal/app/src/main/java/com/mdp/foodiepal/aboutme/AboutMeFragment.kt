package com.mdp.foodiepal.aboutme

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mdp.foodiepal.SharedViewModel
import com.mdp.foodiepal.databinding.FragmentAboutMeBinding

class AboutMeFragment : Fragment() {
    private lateinit var binding: FragmentAboutMeBinding
    private lateinit var context: Context
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutMeBinding.inflate(inflater, container, false)

        loadData(sharedViewModel.getAttributeList())

        sharedViewModel.attributeList.observe(viewLifecycleOwner, Observer { newData ->
            loadData(newData)
        })

        return binding.root
    }

    private fun loadData(list: ArrayList<Attribute>){

        for(item in list){
            var tag = "${item.key}_${item.value}"

            if(binding.llLeft.findViewWithTag<View>(tag) == null){
                var tvKey = TextView(context)
                tvKey.tag = tag
                tvKey.text = "${item.key} : "
                tvKey.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                tvKey.textSize = 16f
                tvKey.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                binding.llLeft.addView(tvKey)

                var tvValue = TextView(context)
                tvValue.text = item.value
                tvValue.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                tvValue.textSize = 16f
                tvValue.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                binding.llRight.addView(tvValue)
            }
        }
    }
}