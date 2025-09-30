package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Calculadora1 : AppCompatActivity() {

    // Creo estas variables para recoger los numeros y la operación del usuario
    private lateinit var txtResultado: TextView
    private var operando1: Double = 0.0
    private var operando2: Double = 0.0
    private var operacion: String = ""
    private var isOperando2 = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculadora1)

        txtResultado = findViewById<TextView>(R.id.txtResultado)


        val botonesNumeros = listOf(
            R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3, R.id.boton4,
            R.id.boton5, R.id.boton6, R.id.boton7, R.id.boton8, R.id.boton9
        )

        for (id in botonesNumeros) {
            findViewById<Button>(id).setOnClickListener {
                val numero = (it as Button).text.toString()
                txtResultado.append(numero)
            }
        }

        findViewById<Button>(R.id.botonSumar).setOnClickListener { seleccionarOperacion("+") }
        findViewById<Button>(R.id.botonRestar).setOnClickListener { seleccionarOperacion("-") }
        findViewById<Button>(R.id.botonMultiplicar).setOnClickListener { seleccionarOperacion("*") }
        findViewById<Button>(R.id.botonDividir).setOnClickListener { seleccionarOperacion("/") }

        findViewById<Button>(R.id.botonResultado).setOnClickListener { calcularResultado() }

    }

    private fun seleccionarOperacion(op: String) {
        operando1 = txtResultado.text.toString().toDoubleOrNull() ?: 0.0
        operacion = op
        txtResultado.text = ""
        isOperando2 = true
    }
    private fun calcularResultado() {
        operando2 = txtResultado.text.toString().toDoubleOrNull() ?: 0.0
        val resultado = when (operacion) {
            "+" -> operando1 + operando2
            "-" -> operando1 - operando2
            "*" -> operando1 * operando2
            "/" -> if (operando2 != 0.0) operando1 / operando2 else "Error"
            else -> "Error"
        }
        txtResultado.text = resultado.toString()
        isOperando2 = false
    }
}