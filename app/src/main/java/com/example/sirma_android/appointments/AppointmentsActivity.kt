package com.example.sirma_android.appointments

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val token = getToken()
        if (token != null) {
            fetchAppointments(token)
            createAppointment(token)  // Llamar aquí a la función para crear una cita
        }
    }

    private fun getToken(): String? {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sirma_android", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }

    private fun fetchAppointments(token: String) {
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.getAppointments("Bearer $token").enqueue(object : Callback<List<Appointment>> {
            override fun onResponse(call: Call<List<Appointment>>, response: Response<List<Appointment>>) {
                if (response.isSuccessful) {
                    val appointments = response.body()
                    // Mostrar citas en RecyclerView (puedes crear un adapter para esto)
                    Toast.makeText(this@AppointmentsActivity, "Citas cargadas", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AppointmentsActivity, "Error al cargar citas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Appointment>>, t: Throwable) {
                Toast.makeText(this@AppointmentsActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Nueva función para crear citas
    private fun createAppointment(token: String) {
        val appointment = Appointment(id = 0, title = "Nueva Cita", scheduled_at = "2025-08-12 10:00:00")

        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.createAppointment("Bearer $token", appointment).enqueue(object : Callback<Appointment> {
            override fun onResponse(call: Call<Appointment>, response: Response<Appointment>) {
                if (response.isSuccessful) {
                    val newAppointment = response.body()
                    // Mostrar mensaje de éxito o actualizar la UI
                    Toast.makeText(this@AppointmentsActivity, "Cita creada", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Appointment>, t: Throwable) {
                // Manejar error
                Toast.makeText(this@AppointmentsActivity, "Error al crear cita", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
