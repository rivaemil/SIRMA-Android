package com.example.sirma_android.appointments

data class Appointment(
    val id: Int,
    val title: String,
    val scheduled_at: String,
    val client: Client,  // Cliente relacionado con la cita
    val vehicle: Vehicle // Vehículo relacionado con la cita
)

data class Client(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String
)

data class Vehicle(
    val id: Int,
    val brand: String,
    val model: String,
    val year: String,
    val plate: String
)
