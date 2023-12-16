package com.mdp.foodiepal.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mdp.foodiepal.MainActivity
import com.mdp.foodiepal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username.text
        val password = binding.password.text

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Add TextWatcher to password EditText
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.btnLogin.setOnClickListener{
            var prefs = getSharedPreferences("login", MODE_PRIVATE)
            val savedUserName = prefs.getString("username", "")
            val savedPassword = prefs.getString("password", "")

            if(username.toString() == savedUserName && password.toString() == savedPassword){
                var intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("username", username.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()

                Toast.makeText(applicationContext, "User logged in successfully!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Invalid user credential!", Toast.LENGTH_LONG).show()
            }

            username.clear()
            password.clear()

        }

        binding.btnRegister.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private fun checkFieldsAndEnableButton() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        // Enable the sign-in button only if both email and password are not empty
        binding.btnLogin.isEnabled = username.isNotEmpty() && password.isNotEmpty()
    }
}