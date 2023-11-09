package com.mdp.assignment4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mdp.assignment4.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE: Int = 1000
        const val REGISTERED_USER = "REGISTERED_USER"
        fun createIntent(context: Context): Intent {
            return Intent(context, CreateAccountActivity::class.java)
        }
    }

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            createAccountClicked()
        }
    }

    private fun createAccountClicked() {
        val firstName = binding.etFirstName.text
        val lastName = binding.etLastName.text
        val email = binding.etEmail.text
        val password = binding.etPass.text

        if(firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
            //showToast("Please fill all the required fields.")
            return
        }

        val user = User(firstName.toString(), lastName.toString(), email.toString(), password.toString())
        setResult(RESULT_OK, intent.putExtra(REGISTERED_USER, user))
        finish()
    }
}