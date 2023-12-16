package com.mdp.foodiepal.aboutme

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.mdp.foodiepal.R
import com.mdp.foodiepal.helper.JsonHelper

class AboutMeDialog(context: Context, private val finishDialog: () -> Unit) : AlertDialog(context) {
    private var attributes: MutableList<Attribute> = mutableListOf()
    private val jsonHelper = JsonHelper(context)

    init {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.about_me_dialog, null)
        setView(dialogView)

        val attributeAsString = jsonHelper.readJsonFromFile("attributes.json")
        if(attributeAsString.isNotBlank())
            attributes = jsonHelper.FromJson(attributeAsString, Array<Attribute>::class.java).toMutableList()

        setButton(
            BUTTON_POSITIVE,
            context.getString(android.R.string.ok)
        ) { _, _ ->
            val attribute = dialogView.findViewById<EditText>(R.id.etAttribute).text.toString()
            val value = dialogView.findViewById<EditText>(R.id.etValue).text.toString()

            if(attribute.isNotBlank() && value.isNotBlank()){
                var obj = Attribute(attribute, value)
                attributes.add(obj)
                jsonHelper.writeClassObjectToFile("attributes.json", attributes)

                dismiss()
                finishDialog()
            }
        }

        setButton(
            BUTTON_NEGATIVE,
            context.getString(android.R.string.cancel)
        ) { _, _ ->
            dismiss()
        }
    }
}
