package com.example.megawinner

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random as Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        //Banco de dados de preferências
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result?.let {
            txtResult.text = "Última aposta: $it"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(
                text,
                txtResult
            )//Ao clicar executa a função que recebe o parametro text e joga no textResult
        }
    }

    private fun numberGenerator(
        text: String,
        txtResult: TextView
    ) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15!", Toast.LENGTH_LONG)
                .show()
            return
        }

        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15!", Toast.LENGTH_LONG)
                .show()
            return
        }

        val numbers =
            mutableSetOf<Int>()
        val random = Random

        while (true) {
            val number =
                random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.apply {
            putString("result", txtResult.text.toString())
            apply()
        }
    }
}

