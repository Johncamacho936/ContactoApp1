package com.example.contactoapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ContactoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        val etNombre = findViewById<TextInputEditText>(R.id.etNombreContacto)
        val etCorreo = findViewById<TextInputEditText>(R.id.etCorreoContacto)
        val etMensaje = findViewById<TextInputEditText>(R.id.etMensajeContacto)
        val btnEnviar = findViewById<Button>(R.id.btnEnviarComentario)

        btnEnviar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val mensaje = etMensaje.text.toString()

            if (nombre.isNotEmpty() && correo.isNotEmpty() && mensaje.isNotEmpty()) {
                enviarCorreo(nombre, correo, mensaje)
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarCorreo(nombre: String, correoUsuario: String, mensaje: String) {

        // --- AQUÍ ES DONDE DEBES PONER TUS DATOS ---
        val miCorreo = "tu_correo@gmail.com"
        val miContraseñaApp = "123456"
        // -------------------------------------------

        // Configuración de propiedades para Gmail
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(miCorreo, miContraseñaApp)
            }
        })

        // El envío de red no puede ir en el hilo principal, usamos Corrutinas
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val mm = MimeMessage(session)
                mm.setFrom(InternetAddress(miCorreo))
                mm.addRecipient(Message.RecipientType.TO, InternetAddress(miCorreo)) // Te lo envías a ti mismo
                mm.subject = "Nuevo comentario de la App - $nombre"
                mm.setText("Nombre: $nombre \nCorreo del contacto: $correoUsuario \n\nMensaje: \n$mensaje")

                Transport.send(mm)

                // Volver al hilo principal para mostrar el mensaje de éxito
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ContactoActivity, "Comentario enviado con éxito", Toast.LENGTH_LONG).show()
                    finish() // Cierra la pantalla de contacto
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ContactoActivity, "Error al enviar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}