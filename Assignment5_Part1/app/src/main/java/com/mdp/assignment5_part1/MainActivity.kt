package com.mdp.assignment5_part1

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.mdp.assignment5_part1.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton

    private var answer1: String = "Type safety"
    private var answer2: String = "compileSdkVersion"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener{
            var message = ""
            var builder = AlertDialog.Builder(this);
            builder.setIcon(R.drawable.alert_icon)
            builder.setTitle("Alert Message")

            var q1 = binding.rg1.checkedRadioButtonId
            if(q1 > -1){
                rb1 = findViewById(q1)
            } else
                message = "Please answer the first question!"

            if(message.isEmpty()){
                var q2 = binding.rg2.checkedRadioButtonId
                if(q2 > -1){
                    rb2 = findViewById(q2)
                } else
                    message = "Please answer the second question!"
            }

            if(message.isEmpty()) {
                var correctAnswer = 0;
                if(rb1.text.equals(answer1))
                    correctAnswer++;
                if(rb2.text.equals(answer2))
                    correctAnswer++;

                val percentage = correctAnswer * 100 / 2
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val current = LocalDateTime.now().format(formatter)
                message = "Congratulations! You submitted on the $current and you achieved $percentage%" //${LocalDateTime.now()}
            }

            builder.setMessage(message)
            builder.setNeutralButton("OK") { _, _ -> }

            val alert: AlertDialog = builder.create()
            alert.show()
        }

        binding.btnReset.setOnClickListener{
            binding.rg1.clearCheck()
            binding.rg2.clearCheck()
        }
    }
}
