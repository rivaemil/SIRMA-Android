package com.example.sirma_android.data

import com.squareup.moshi.Json
import java.time.OffsetDateTime

data class AuthResponse(
    val token: String,
    val user: UserDto,
    val role: String // "client" | "mechanic" | "admin"
)

data class UserDto(
    val id: Long,
    val name: String?,
    val email: String
)

data class ClientDto(
    val id: Long,
    @Json(name = "user_id") val userId: Long,
    val name: String?,
    val phone: String?,
    val email: String?
)

data class VehicleDto(
    val id: Long,
    val marca: String?,
    val modelo: String?,
    val anio: Int?,
    val placas: String?
)

data class AppointmentDto(
    val id: Long,
    val title: String,
    @Json(name = "client_id") val clientId: Long,
    @Json(name = "vehicle_id") val vehicleId: Long,
    // Laravel te devuelve "YYYY-MM-DDTHH:MM:SS" (a veces sin zona). Lo parseamos como OffsetDateTime con desugaring.
    @Json(name = "scheduled_at") val scheduledAt: String,
    val client: ClientDto?,
    val vehicle: VehicleDto?
)

// Helper para convertir a tipos java.time cuando lo necesites:
fun AppointmentDto.scheduledAtAsOffset(): OffsetDateTime =
    OffsetDateTime.parse(
        if (scheduledAt.endsWith("Z") || scheduledAt.contains("+")) scheduledAt
        else "${scheduledAt}Z" // fallback si viene sin zona
    )
