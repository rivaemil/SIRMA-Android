package com.example.sirma_android.data
// data/remote/ApiService.kt
import com.example.sirma_android.data.*
import retrofit2.http.*

interface ApiService {

    // Iniciar sesión
    @POST("login")
    suspend fun login(@Body body: Map<String, String>): AuthResponse

    // Obtener citas del cliente (ya filtrado por el backend)
    @GET("my-appointments")
    suspend fun getMyAppointments(
        @Query("desde") desde: String? = null, // formato: "YYYY-MM-DD"
        @Query("hasta") hasta: String? = null  // formato: "YYYY-MM-DD"
    ): List<AppointmentDto>

    // Obtener detalles de una cita (solo lectura)
    @GET("appointments/{id}")
    suspend fun getAppointment(@Path("id") id: Long): AppointmentDto
}
