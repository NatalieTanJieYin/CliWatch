package com.example.cliwatch.calculator.data

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cliwatch.R
import com.example.cliwatch.calculator.CalculatorFragment
import com.example.cliwatch.calculator.CalculatorFragment2

class Launcher : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val track = findViewById<Button>(R.id.tracker)
        val launcher = findViewById<Button>(R.id.button2)
        launcher.setOnClickListener {
            val intent= Intent(this,CalculatorFragment::class.java)
            startActivity(intent)
        }
        track.setOnClickListener {
            val intent= Intent(this,CalculatorFragment2::class.java)
            startActivity(intent)
        }
    }
}