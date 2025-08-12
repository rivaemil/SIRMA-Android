package com.example.sirma_android.data


import com.example.sirma_android.data.AppointmentDto
import com.example.sirma_android.data.AuthResponse
import com.example.sirma_android.data.ApiService

class AppointmentRepository(private val api: ApiService) {

    suspend fun login(email: String, password: String): AuthResponse =
        api.login(mapOf("email" to email, "password" to password))

    suspend fun myAppointments(desde: String?, hasta: String?): List<AppointmentDto> =
        api.getMyAppointments(desde, hasta)

    suspend fun appointmentDetail(id: Long): AppointmentDto =
        api.getAppointment(id)
}
