package com.example.sirma_android.data

import com.example.sirma_android.appointments.Appointment
import com.example.sirma_android.login.LoginRequest
import com.example.sirma_android.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    // Ruta para login
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // Ruta para obtener las citas del usuario
    @GET("my-appointments")  // Ruta correcta según el backend
    fun getAppointments(
        @Header("Authorization") token: String
    ): Call<List<Appointment>>  // Recibe la lista de citas del usuario autenticado

    // Ruta para crear una cita
    @POST("appointments")
    fun createAppointment(
        @Header("Authorization") token: String,
        @Body appointment: Appointment
    ): Call<Appointment>  // Recibe la cita creada como respuesta
}
