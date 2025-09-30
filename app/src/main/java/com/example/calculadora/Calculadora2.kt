package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.mozilla.javascript.Context
import org.mozilla.javascript.Script
import org.mozilla.javascript.Scriptable

class Calculadora2 : AppCompatActivity() {

    private lateinit var txtResultado: TextView
    private var expresion: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculadora2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtResultado = findViewById<TextView>(R.id.txtResultado)

        // Lista de botones con su texto a agregar
        val botones = listOf(
            R.id.boton0 to "0", R.id.boton1 to "1", R.id.boton2 to "2", R.id.boton3 to "3",
            R.id.boton4 to "4", R.id.boton5 to "5", R.id.boton6 to "6", R.id.boton7 to "7",
            R.id.boton8 to "8", R.id.boton9 to "9", R.id.botonSumar to "+", R.id.botonRestar to "-",
            R.id.botonMultiplicar to "*", R.id.botonDividir to "/", R.id.botonResto to "%",
            R.id.botonPotencia to "^", R.id.botonDecimal to "."
        )

        // Asignar acción a cada botón
        for ((id, valor) in botones) {
            findViewById<Button>(id).setOnClickListener {
                expresion += valor
                txtResultado.text = expresion
            }
        }

        // Botón borrar uno
        findViewById<Button>(R.id.borrarUno).setOnClickListener {
            if (expresion.isNotEmpty()) {
                expresion = expresion.dropLast(1)
                txtResultado.text = expresion.ifEmpty { "0" }
            }
        }

        // Botón reset
        findViewById<Button>(R.id.borrarTodo).setOnClickListener {
            expresion = ""
            txtResultado.text = "0"
        }

        // Botón resultado
        findViewById<Button>(R.id.botonResultado).setOnClickListener {
            try {

                val ctx = Context.enter()
                ctx.optimizationLevel = -1
                val scope: Scriptable = ctx.initStandardObjects()

                // Para la potencia, uso Math.pow
                val expEval = expresion.replace(Regex("(\\d+)\\^(\\d+)")) {
                    "Math.pow(${it.groupValues[1]},${it.groupValues[2]})"
                }

                // Para el resultado, uso la función de JS eval
                val resultado = ctx.evaluateString(scope, expEval, "JavaScript", 1, null)
                txtResultado.text = resultado.toString()
                expresion = resultado.toString()


            }
            catch (e: Exception) {
                txtResultado.text = "Error"
                expresion = ""
            }
            finally {
                Context.exit()
            }
        }


    }
}