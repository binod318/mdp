package com.mdp.foodiepal.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mdp.foodiepal.R
import com.mdp.foodiepal.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onUserRegister(view: View){
        val userName = binding.username.text
        val password = binding.password.text
        val confirmPassword = binding.confirmPassword.text

        if(userName.isNullOrBlank() || password.isNullOrBlank() || confirmPassword.isNullOrBlank()){
            var builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.error)
            builder.setTitle("Error")
            builder.setMessage("Email or password can not be empty!")
            builder.setPositiveButton("Ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else if(password.toString() != confirmPassword.toString()){
            var builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.error)
            builder.setTitle("Error")
            builder.setMessage("Password mismatch!")
            builder.setPositiveButton("Ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            //save data in shared preference
            var prefs = getSharedPreferences("login", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("username", userName.toString())
            editor.putString("password", password.toString())
            editor.apply()

            //go back
            finish()

            Toast.makeText(applicationContext, "User registered successfully!", Toast.LENGTH_LONG).show()
        }
    }
}