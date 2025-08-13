package com.example.sirma_android

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sirma_android.LoginActivity
import com.example.sirma_android.appointments.AppointmentsActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Verificamos si el token está presente
        val token = getToken()

        if (token.isNullOrEmpty()) {
            // Si no hay token, redirigir al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)  // Redirigir al login
            Log.d("MainActivity", "Token no encontrado, redirigiendo a LoginActivity")
        } else {
            // Si el token existe, redirigir a AppointmentsActivity
            val intent = Intent(this, AppointmentsActivity::class.java)
            startActivity(intent)  // Redirigir a las citas
            Log.d("MainActivity", "Token encontrado, redirigiendo a AppointmentsActivity")
        }

        // No usamos finish() inmediatamente, ya que el ciclo de vida de la actividad está en juego
    }

    // Recuperar el token desde SharedPreferences
    private fun getToken(): String? {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sirma_android", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        Log.d("MainActivity", "Token recuperado: $token")
        return token
    }
}
