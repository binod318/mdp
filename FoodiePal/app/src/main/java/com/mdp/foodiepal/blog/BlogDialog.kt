package com.mdp.foodiepal.blog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.mdp.foodiepal.R
import com.mdp.foodiepal.helper.Helper
import com.mdp.foodiepal.helper.JsonHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BlogDialog(context: Context, private val finishDialog: () -> Unit) : AlertDialog(context) {
    private val jsonHelper = JsonHelper(context)
    private var blogs: MutableList<Blog> = mutableListOf()

    init {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.blog_dialog, null)

        handlePhotoSelect(view)

        setView(view)

        setTitle("Add new post")

        val blogsString = jsonHelper.readJsonFromFile("blogs.json")
        if(blogsString.isNotBlank())
            blogs = jsonHelper.FromJson(blogsString, Array<Blog>::class.java).toMutableList()

        setButton(BUTTON_POSITIVE, "Add") { _, _ ->

            val title = view.findViewById<EditText>(R.id.etTitle).text.toString()
            val author = view.findViewById<EditText>(R.id.etAuthor).text.toString()
            val desc = view.findViewById<EditText>(R.id.etDescription).text.toString()
            val thumbnail = view.findViewById<ImageView>(R.id.imageThumbnail).tag as? Int ?: 0

            if(thumbnail > 0 && title.isNotBlank() && author.isNotBlank() && desc.isNotBlank()){

                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formattedDateTime = currentDateTime.format(formatter)

                val blog = Blog(title,desc, thumbnail, author,formattedDateTime)
                blogs.add(blog)
                val jsonString = jsonHelper.ToJson(blogs)

                jsonHelper.writeJsonToFile("blogs.json", jsonString)

                dismiss()
                finishDialog()
            }

        }

        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dismiss()
        }
    }

    private fun handlePhotoSelect(view: View) {

        val imageView = view.findViewById<TextView>(R.id.imageThumbnail) as ImageView
        val spinner = view.findViewById<Button>(R.id.spinner) as Spinner

        var blogDrawables = Helper().getDrawablesByType("blog_")

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

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}