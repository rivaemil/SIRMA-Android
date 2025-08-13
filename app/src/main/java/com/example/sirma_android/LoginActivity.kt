package com.example.sirma_android

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sirma_android.data.ApiService
import com.example.sirma_android.login.LoginRequest
import com.example.sirma_android.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = "cliente@example.com"  // Puedes tomarlo de un campo EditText
        val password = "contraseña"  // También del campo correspondiente

        val loginRequest = LoginRequest(email, password)
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    // Guardar el token en SharedPreferences
                    saveToken(token)
                } else {
                    // Manejar error
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Manejar error de red
            }
        })
    }

    private fun saveToken(token: String?) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE)
        sharedPreferences.edit {
            putString("auth_token", token)
        }
    }
}
