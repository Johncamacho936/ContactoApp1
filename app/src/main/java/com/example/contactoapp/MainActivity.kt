package com.example.contactoapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNombre = findViewById<TextInputEditText>(R.id.etNombre)
        val etFecha = findViewById<TextInputEditText>(R.id.etFecha)
        val etTelefono = findViewById<TextInputEditText>(R.id.etTelefono)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etDescripcion = findViewById<TextInputEditText>(R.id.etDescripcion)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)

        // Configurar el DatePicker para el campo de Fecha
        etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val año = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                etFecha.setText(fechaSeleccionada)
            }, año, mes, dia)

            datePickerDialog.show()
        }

        // Configurar el botón "Siguiente" para ir a la Actividad 2
        btnSiguiente.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)

            intent.putExtra("NOMBRE", etNombre.text.toString())
            intent.putExtra("FECHA", etFecha.text.toString())
            intent.putExtra("TELEFONO", etTelefono.text.toString())
            intent.putExtra("EMAIL", etEmail.text.toString())
            intent.putExtra("DESCRIPCION", etDescripcion.text.toString())

            startActivity(intent)
        }
    }

    // --- NUEVAS FUNCIONES PARA EL MENÚ DE OPCIONES ---

    // 1. Esta función "infla" (dibuja) el menú de los 3 puntitos usando tu archivo XML
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)
        return true
    }

    // 2. Esta función detecta qué opción del menú tocaste y abre la pantalla correspondiente
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_contacto -> {
                // Abre la pantalla de Contacto
                startActivity(Intent(this, ContactoActivity::class.java))
                true
            }
            R.id.menu_acerca_de -> {
                // Abre la pantalla de Acerca De
                startActivity(Intent(this, AcercaDeActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}