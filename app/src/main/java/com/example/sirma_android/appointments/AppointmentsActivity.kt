package com.example.sirma_android.appointments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        val sp = getSharedPreferences("sirma_android", MODE_PRIVATE)
        val token = sp.getString("auth_token", null)
        Log.d("AppointmentsActivity", "Token recuperado: $token")
        return token
    }

    private fun fetchAppointments(token: String) {
        val api = RetrofitClient.getClient().create(ApiService::class.java)

        api.getAppointments("Bearer $token").enqueue(object : Callback<List<Appointment>> {
            override fun onResponse(call: Call<List<Appointment>>, res: Response<List<Appointment>>) {
                if (res.isSuccessful) {
                    val list = res.body().orEmpty()
                    updateRecyclerView(list)
                } else {
                    Log.d("AppointmentsActivity", "HTTP ${res.code()} ${res.errorBody()?.string()}")
                    Toast.makeText(this@AppointmentsActivity, "No se pudieron cargar las citas", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Appointment>>, t: Throwable) {
                Toast.makeText(this@AppointmentsActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun updateRecyclerView(appointments: List<Appointment>) {
        val adapter = AppointmentsAdapter(appointments)
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(this)
        appointmentsRecyclerView.adapter = adapter
    }

}
