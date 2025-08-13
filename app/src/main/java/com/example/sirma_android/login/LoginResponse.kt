
package com.example.sirma_android.login

import com.example.sirma_android.User


data class LoginResponse(
    val token: String,
    val user: User
)
