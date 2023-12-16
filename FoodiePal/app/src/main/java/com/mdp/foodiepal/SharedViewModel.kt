package com.mdp.foodiepal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdp.foodiepal.aboutme.Attribute
import com.mdp.foodiepal.blog.Blog
import com.mdp.foodiepal.mealplanner.MealPlanner
import com.mdp.foodiepal.recipe.Recipe

class SharedViewModel : ViewModel() {

    //Recipes
    private val _reciperList = MutableLiveData<ArrayList<Recipe>>()

    val recipeList: LiveData<ArrayList<Recipe>>
        get() = _reciperList

    fun setRecipeList(newData: ArrayList<Recipe>) {
        _reciperList.value = newData
    }

    fun getRecipeList() : ArrayList<Recipe> {
        return recipeList.value ?: ArrayList()
    }


    //MealPlanner
    private val _mealPlannerList = MutableLiveData<ArrayList<MealPlanner>>()

    val mealPlannerList: LiveData<ArrayList<MealPlanner>>
        get() = _mealPlannerList

    fun setMealPlannerList(newData: ArrayList<MealPlanner>) {
        _mealPlannerList.value = newData
    }

    fun getMealPlannerList() : ArrayList<MealPlanner> {
        return mealPlannerList.value ?: ArrayList()
    }


    //Blog
    private val _blogList = MutableLiveData<ArrayList<Blog>>()

    val blogList: LiveData<ArrayList<Blog>>
        get() = _blogList

    fun setBlogList(newData: ArrayList<Blog>) {
        _blogList.value = newData
    }

    fun getBlogList() : ArrayList<Blog> {
        return blogList.value ?: ArrayList()
    }


    //AboutMe
    private val _attributeList = MutableLiveData<ArrayList<Attribute>>()

    val attributeList: LiveData<ArrayList<Attribute>>
        get() = _attributeList

    fun setAttributeList(newData: ArrayList<Attribute>) {
        _attributeList.value = newData
    }

    fun getAttributeList() : ArrayList<Attribute> {
        return attributeList.value ?: ArrayList()
    }
}