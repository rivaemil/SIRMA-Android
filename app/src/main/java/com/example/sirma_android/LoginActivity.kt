package com.example.sirma_android

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sirma_android.data.ApiService
import com.example.sirma_android.login.LoginRequest
import com.example.sirma_android.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.edit
import com.example.sirma_android.appointments.AppointmentsActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)

                val apiService = RetrofitClient.getClient().create(ApiService::class.java)

                apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            if (!token.isNullOrEmpty()) {
                                saveToken(token.trim())  // Guardamos el token
                                Log.d("LoginActivity", "Token guardado correctamente: $token")

                                // Redirigir a AppointmentsActivity
                                val intent = Intent(this@LoginActivity, AppointmentsActivity::class.java)
                                startActivity(intent)
                                finish()  // Finalizamos LoginActivity para que no regrese
                            } else {
                                Log.d("LoginActivity", "Token es nulo o vacío")
                            }
                        } else {
                            Log.d("LoginActivity", "Error al recibir el token: ${response.errorBody()?.string()}")
                            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("LoginActivity", "Error de red: ${t.message}")
                        Toast.makeText(this@LoginActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Por favor ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Guardar el token en SharedPreferences
    private fun saveToken(token: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sirma_android", MODE_PRIVATE)
        sharedPreferences.edit().putString("auth_token", token).apply()
        Log.d("LoginActivity", "Token guardado correctamente: $token")
    }
}
