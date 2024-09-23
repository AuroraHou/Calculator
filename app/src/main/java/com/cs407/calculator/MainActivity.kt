package com.cs407.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val firstNumber = findViewById<EditText>(R.id.firstNumber)
        val secondNumber = findViewById<EditText>(R.id.secondNumber)
        val addButton = findViewById<Button>(R.id.addButton)
        val subtractButton = findViewById<Button>(R.id.subtractButton)
        val multiplyButton = findViewById<Button>(R.id.multiplyButton)
        val divideButton = findViewById<Button>(R.id.divideButton)

        // Function to handle button clicks and perform calculations
        fun calculate(operation: (Int, Int) -> Int, operationName: String) {
            val num1 = firstNumber.text.toString().toIntOrNull()
            val num2 = secondNumber.text.toString().toIntOrNull()

            if (num1 == null || num2 == null) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return
            }

            // Handle divide by zero
            if (operationName == "division" && num2 == 0) {
                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                return
            }

            val result = operation(num1, num2)
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("RESULT", result)
            startActivity(intent)
        }

        // Add button click logic
        addButton.setOnClickListener {
            calculate({ a, b -> a + b }, "addition")
        }

        subtractButton.setOnClickListener {
            calculate({ a, b -> a - b }, "subtraction")
        }

        multiplyButton.setOnClickListener {
            calculate({ a, b -> a * b }, "multiplication")
        }

        divideButton.setOnClickListener {
            calculate({ a, b -> a / b }, "division")
        }
    }
}