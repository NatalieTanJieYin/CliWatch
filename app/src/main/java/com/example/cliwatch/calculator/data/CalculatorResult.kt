package com.example.cliwatch.calculator.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.cliwatch.R

class CalculatorResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val imageview= findViewById<ImageView>(R.id.imageView)
        val id1 = R.drawable.sadearth
        val id2 = R.drawable.thankyou

        val message = intent.getStringExtra("MSG")
        val messageTextView: TextView = findViewById(R.id.textView6)
        messageTextView.text = message

        val value = message?.toIntOrNull()
        if (value != null) {
            if(value<30){
                imageview.setImageResource(id2)

            }
            else if (value>30){
                imageview.setImageResource(id1)

            }
        }


    }
}