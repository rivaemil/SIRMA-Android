package com.example.sirma_android.data

import com.example.sirma_android.data.*
import retrofit2.http.*

interface ApiService {

    @POST("login")
    suspend fun login(@Body body: Map<String, String>): AuthResponse

    // Solo cliente: el backend ya filtra por el user->client
    @GET("my-appointments")
    suspend fun getMyAppointments(
        @Query("desde") desde: String? = null, // "YYYY-MM-DD"
        @Query("hasta") hasta: String? = null
    ): List<AppointmentDto>

    @GET("appointments/{id}")
    suspend fun getAppointment(@Path("id") id: Long): AppointmentDto
}
