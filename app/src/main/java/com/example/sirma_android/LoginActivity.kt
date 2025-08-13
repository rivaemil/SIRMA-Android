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

        val emailEt = findViewById<EditText>(R.id.editTextEmail)
        val passEt  = findViewById<EditText>(R.id.editTextPassword)
        val btn     = findViewById<Button>(R.id.buttonLogin)

        btn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passEt.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val api = RetrofitClient.getClient().create(ApiService::class.java)
            api.login(LoginRequest(email, password)).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, res: Response<LoginResponse>) {
                    if (res.isSuccessful) {
                        val token = res.body()?.token?.trim()
                        if (!token.isNullOrEmpty()) {
                            getSharedPreferences("sirma_android", MODE_PRIVATE)
                                .edit().putString("auth_token", token).apply()

                            startActivity(Intent(this@LoginActivity, AppointmentsActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Token vacío", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        Log.d("LoginActivity", "Error: ${res.errorBody()?.string()}")
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}



