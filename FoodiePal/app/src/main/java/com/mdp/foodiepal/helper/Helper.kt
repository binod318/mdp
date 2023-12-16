package com.mdp.foodiepal.helper

import com.mdp.foodiepal.R
import com.mdp.foodiepal.Resource

class Helper {

    private fun getAllDrawableResourceNames(): List<Resource> {
        val drawableResourceNames = mutableListOf<Resource>()

        try {
            val drawableClass = R.drawable::class.java
            val fields = drawableClass.fields

            for (field in fields) {
                if (field.type == Int::class.javaPrimitiveType) {
                    drawableResourceNames.add(Resource(field.getInt(null), field.name))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return drawableResourceNames
    }

    fun getDrawablesByType(type:String): List<String> {
        var allDrawableResourceNames: List<Resource> = getAllDrawableResourceNames()
        var blogDrawables = allDrawableResourceNames.filter{ o -> o.name.contains(type)}.map { o -> o.name }.toMutableList()
        blogDrawables.add(0, "Select photo to upload")

        return blogDrawables
    }

    fun findDrawableByName(name:String): Resource? {
        var allDrawableResourceNames: List<Resource> = getAllDrawableResourceNames()
        return allDrawableResourceNames.firstOrNull{ o -> o.name == name }
    }
}