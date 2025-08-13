package com.example.sirma_android.appointments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sirma_android.R
import com.example.sirma_android.RetrofitClient
import com.example.sirma_android.data.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentsActivity : AppCompatActivity() {

    private lateinit var appointmentsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        appointmentsRecyclerView = findViewById(R.id.recycler_view_appointments)

        // Obtener el token desde SharedPreferences
        val token = getToken()
        if (token != null) {
            fetchAppointments(token)
        } else {
            Log.d("AppointmentsActivity", "Token es nulo o vacío")
        }
    }

    private fun getToken(): String? {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sirma_android", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        Log.d("AppointmentsActivity", "Token recuperado: $token")
        return token
    }

    private fun fetchAppointments(token: String) {
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.getAppointments("Bearer $token").enqueue(object : Callback<List<Appointment>> {
            override fun onResponse(call: Call<List<Appointment>>, response: Response<List<Appointment>>) {
                if (response.isSuccessful) {
                    val appointments = response.body() ?: emptyList()
                    Log.d("AppointmentsActivity", "Citas recibidas: $appointments")
                    updateRecyclerView(appointments)
                } else {
                    Log.d("AppointmentsActivity", "Error al obtener las citas: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Appointment>>, t: Throwable) {
                Log.d("AppointmentsActivity", "Error de red: ${t.message}")
            }
        })
    }

    private fun updateRecyclerView(appointments: List<Appointment>) {
        val adapter = AppointmentsAdapter(appointments)
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(this)
        appointmentsRecyclerView.adapter = adapter
    }
}
