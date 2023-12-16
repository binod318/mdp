package com.mdp.foodiepal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mdp.foodiepal.aboutme.AboutMeFragment
import com.mdp.foodiepal.blog.BlogFragment
import com.mdp.foodiepal.contact.ContactFragment
import com.mdp.foodiepal.mealplanner.MealPlannerFragment
import com.mdp.foodiepal.recipe.RecipeFragment

class MyPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecipeFragment()
            1 -> MealPlannerFragment()
            2 -> BlogFragment()
            3 -> ContactFragment()
            4 -> AboutMeFragment()
            else -> Fragment()
        }
    }
}