package com.mdp.assignment4

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.ComponentActivity
import com.mdp.assignment4.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addUsers()

        binding.btnSignIn.setOnClickListener {
            signInClicked()
        }
        binding.tvForgotPassword.setOnClickListener {
            clickedForgotPassword()
        }
        binding.btnCreateAccount.setOnClickListener {
            startActivityForResult(CreateAccountActivity.createIntent(this), CreateAccountActivity.REQUEST_CODE)
        }
    }

    private fun addUsers() {
        for (i in 1..5) {
            users.add(User("User$i", "User$i", "User$i@gmail.com", "User$i"))
        }
    }

    private fun signInClicked() {
        val username = binding.etEmail.text.toString()
        val password = binding.etPass.text.toString()

        if (username.isBlank() || password.isBlank()) {
            showToast("Please fill in all fields")
            return
        }

        if (users.any { user -> user.userName.lowercase() == username.lowercase() && user.password == password }) {
            startActivity(ShoppingActivity.createIntent(this, username))
            finish()
        } else {
            showToast("Invalid credentials, please try again.")
            binding.etPass.text?.clear()
        }
    }

    private fun clickedForgotPassword() {
        val password = getPasswordForUserName(binding.etEmail.text)

        if(password == null) {
            showToast("No user found with this email")
            return
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("${binding.etEmail.text}"))
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Password Recovery: $password"
        )
        startActivity(intent)
    }

    private fun getPasswordForUserName(text: Editable?): String? {
        val foundUser = users.firstOrNull { user ->
            user.userName.lowercase() == text.toString().lowercase()
        }
        return foundUser?.password
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == CreateAccountActivity.REQUEST_CODE) {
            try {
                val newUser = data?.getParcelableExtra<User>(CreateAccountActivity.REGISTERED_USER)
                if(newUser != null) {
                    users.add(newUser)
                    showToast("Account created successfully")
                }
            }
            catch (e: Exception) {
                showToast("Error creating account, please try again.")
            }
        }
    }
}