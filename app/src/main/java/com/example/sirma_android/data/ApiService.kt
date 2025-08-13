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

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("appointments")
    fun getAppointments(@Header("Authorization") token: String): Call<List<Appointment>>

    @POST("appointments")
    fun createAppointment(@Header("Authorization") token: String, @Body appointment: Appointment): Call<Appointment>
}
