// data/ApiService.kt
package com.example.sirma_android.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(@Body body: Map<String, String>): AuthResponse

    @GET("my-appointments")
    suspend fun getMyAppointments(
        @Query("desde") desde: String?,
        @Query("hasta") hasta: String?
    ): List<AppointmentDto>

    @GET("appointments/{id}")
    suspend fun getAppointment(@Path("id") id: Long): AppointmentDto
}

// Nueva función para crear la instancia de Retrofit
fun provideApiService(): ApiService {
    return Retrofit.Builder()
        .baseUrl("http://192.168.100.7:8000/api/")  // O tu URL real aquí
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
