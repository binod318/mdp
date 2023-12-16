package com.mdp.foodiepal.helper

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class JsonHelper(private val context: Context?) {
    fun ToJson(data: Any) : String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(data)
    }

    fun <T> FromJson(jsonString: String, classOfT: Class<T>): T {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(jsonString, classOfT)
    }

    fun <T> ReadFromJson(jsonName: String, classOfT: Class<T>): T? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = readJsonFromFile(jsonName)
        if(jsonString.isNullOrEmpty()) return null
        return gson.fromJson(jsonString, classOfT)
    }

    fun writeClassObjectToFile(fileName: String, data: Any) {
        var jsonString = ToJson(data)
        writeJsonToFile(fileName,jsonString)
    }

    fun writeJsonToFile(fileName: String, jsonString: String) {
        try {
            val fileOutputStream: FileOutputStream? = context?.openFileOutput(fileName, Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            outputStreamWriter.write(jsonString)
            outputStreamWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readJsonFromFile(fileName: String): String {
        val stringBuilder = StringBuilder()
        try {
            val fileInputStream: FileInputStream? = context?.openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line).append('\n')
                line = bufferedReader.readLine()
            }
            fileInputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}
