package com.example.contactoapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2) // Asegúrate de que apunte a activity_main2

        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvFecha = findViewById<TextView>(R.id.tvFecha)
        val tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        val btnEditar = findViewById<Button>(R.id.btnEditar)

        // 1. Recuperar los datos que vienen desde la Actividad 1
        val nombre = intent.getStringExtra("NOMBRE")
        val fecha = intent.getStringExtra("FECHA")
        val telefono = intent.getStringExtra("TELEFONO")
        val email = intent.getStringExtra("EMAIL")
        val descripcion = intent.getStringExtra("DESCRIPCION")

        // 2. Mostrar los datos en la pantalla
        tvNombre.text = "Nombre: $nombre"
        tvFecha.text = "Fecha: $fecha"
        tvTelefono.text = "Teléfono: $telefono"
        tvEmail.text = "Email: $email"
        tvDescripcion.text = "Descripción: $descripcion"

        // 3. Configurar el botón para editar datos
        btnEditar.setOnClickListener {
            // Al llamar a finish(), esta segunda actividad se cierra.
            // Automáticamente regresas a la primera pantalla y, como Android
            // guarda el estado, todos los datos seguirán ahí precargados listos para editarse.
            finish()
        }
    }
}