package com.mdp.foodiepal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdp.foodiepal.aboutme.AboutMeDialog
import com.mdp.foodiepal.aboutme.Attribute
import com.mdp.foodiepal.blog.Blog
import com.mdp.foodiepal.blog.BlogDialog
import com.mdp.foodiepal.databinding.ActivityMainBinding
import com.mdp.foodiepal.helper.JsonHelper
import com.mdp.foodiepal.mealplanner.MealPlanner
import com.mdp.foodiepal.mealplanner.MealPlannerDialog
import com.mdp.foodiepal.recipe.Recipe
import com.mdp.foodiepal.recipe.RecipeDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var jsonHelper: JsonHelper = JsonHelper(this)
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //load data for all fragments
        loadRecipes()
        loadMealPlanners()
        loadBlogs()
        loadAboutMe()

        val myPageAdapter = MyPageAdapter(this)
        binding.myViewPager.adapter = myPageAdapter
        binding.myTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(binding.myTabLayout, binding.myViewPager){tab,position ->
            when (position) {
                0 -> tab.text = "Recipes"
                1 -> tab.text = "Meal Planner"
                2 -> tab.text = "Blog"
                3 -> tab.text = "Contact"
                4 -> tab.text = "About Me"
            }
        }.attach()

        // Update BottomNavigationView tab selection
        binding.myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.menu_recipe
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.menu_meal_planner
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.menu_blog
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.myFaButton.setOnClickListener { view ->
            when (binding.myTabLayout.selectedTabPosition) {
                0 -> { //Recipe
                    var builder = RecipeDialog(this) {
                        Snackbar.make(view, "New Recipe added", Snackbar.LENGTH_LONG)
                                .setAction("Ok", null).show()
                        loadRecipes()
                    }
                    builder.show()
                }
                1 -> { //Meal Planner
                    var builder = MealPlannerDialog(this){
                        Snackbar.make(view, "New Meal plan added", Snackbar.LENGTH_LONG)
                            .setAction("Ok", null).show()
                        loadMealPlanners()
                    }
                    builder.show()
                }
                2 -> {
                    var builder = BlogDialog(this){
                        Snackbar.make(view, "New Blog added", Snackbar.LENGTH_LONG)
                            .setAction("Ok", null).show()
                        loadBlogs()
                    }
                    builder.show()

                }
                3 -> {
                    Toast.makeText(this, "No action added!", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    var builder = AboutMeDialog(this){
                        Snackbar.make(view, "New attribute added", Snackbar.LENGTH_LONG)
                            .setAction("Ok", null).show()
                        loadAboutMe()
                    }
                    builder.show()
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_recipe->setActiveTab(0)
                R.id.menu_meal_planner->setActiveTab(1)
                R.id.menu_blog->setActiveTab(2)
            }
            true
        }
    }

    private fun setActiveTab(tabIndex: Int) {
        binding.myViewPager.currentItem = tabIndex
        val tab: TabLayout.Tab? = binding.myTabLayout.getTabAt(tabIndex)
        tab?.select()
    }

    private fun loadRecipes(){
        var recipes = jsonHelper.ReadFromJson("recipes.json", Array<Recipe>::class.java)?.toCollection(ArrayList()) ?: ArrayList()
        sharedViewModel.setRecipeList(recipes)
    }

    private fun loadMealPlanners(){
        var mealplanners = jsonHelper.ReadFromJson("meal_planners.json", Array<MealPlanner>::class.java)?.toCollection(ArrayList()) ?: ArrayList()
        sharedViewModel.setMealPlannerList(mealplanners)
    }

    private fun loadBlogs(){
        var blogs = jsonHelper.ReadFromJson("blogs.json", Array<Blog>::class.java)?.toCollection(ArrayList()) ?: ArrayList()
        sharedViewModel.setBlogList(blogs)
    }

    private fun loadAboutMe(){
        var attributes = jsonHelper.ReadFromJson("attributes.json", Array<Attribute>::class.java)?.toCollection(ArrayList()) ?: ArrayList()
        sharedViewModel.setAttributeList(attributes)
    }

    fun onPhoneClick(view: View) {
        val uri = Uri.parse("tel:8002224444")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }

    fun onEmailClick(view: View) {
        val recipient = "binod.gurung@miu.edu"
        val subject = "Food review"
        val message = "Dear chef,\n\nI am writing this email to you to share my thought regarding your food. I have enjoyed your food. I need some time of you if possible to talk about it.\nThis is a link to continue our conversation.\nhttps://myconversation.com\n\nThank you.\n\nRegards,\nYours Customer!"

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        startActivity(intent)
    }
}