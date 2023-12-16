package com.mdp.foodiepal.recipe

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Spinner
import com.mdp.foodiepal.R
import com.mdp.foodiepal.helper.Helper
import com.mdp.foodiepal.helper.JsonHelper

class RecipeDialog(context: Context, private val finishDialog: () -> Unit) : AlertDialog(context) {
    private val jsonHelper = JsonHelper(context)
    private var recipes: MutableList<Recipe> = mutableListOf()

    init {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.recipe_dialog, null)

        handlePhotoSelect(view)
        setView(view)

        setTitle("Add new recipe")

        setButton(BUTTON_POSITIVE, "Add") { _, _ ->

            val blogsString = jsonHelper.readJsonFromFile("recipes.json")
            if(blogsString.isNotBlank())
                recipes = jsonHelper.FromJson(blogsString, Array<Recipe>::class.java).toMutableList()

            val recipeName = view.findViewById<EditText>(R.id.etRecipe).text.toString()
            val cookingTime = view.findViewById<EditText>(R.id.etCookingTime).text.toString()
            val ingredients = view.findViewById<EditText>(R.id.etIngredients).text.toString()
            val instruction = view.findViewById<EditText>(R.id.etInstruction).text.toString()
            val thumbnail = view.findViewById<ImageView>(R.id.imageThumbnail).tag as? Int ?: 0
            val rating = view.findViewById<RatingBar>(R.id.rvRating).rating

            if(thumbnail > 0 && recipeName.isNotBlank() && cookingTime.isNotBlank() && ingredients.isNotBlank() && instruction.isNotBlank()){

                val recipe = Recipe(recipeName,cookingTime,rating,ingredients,instruction,thumbnail)
                recipes.add(recipe)
                val jsonString = jsonHelper.ToJson(recipes)

                jsonHelper.writeJsonToFile("recipes.json", jsonString)

                dismiss()
                finishDialog()
            }

            dismiss()
        }

        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun handlePhotoSelect(view: View) {

        val imageView = view.findViewById<ImageView>(R.id.imageThumbnail)
        val spinner = view.findViewById<Spinner>(R.id.spinner)

        var blogDrawables = Helper().getDrawablesByType("recipe_")

        //set adapter for spinner
        var aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, blogDrawables)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()

                var resource = Helper().findDrawableByName(item)
                if(resource != null) {
                    imageView.setImageResource(resource.id)
                    imageView.tag = resource.id
                } else {
                    imageView.setImageResource(0)
                    imageView.tag = 0
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}