package com.example.sirma_android.data

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import com.example.sirma_android.data.AuthStore
import kotlinx.coroutines.flow.firstOrNull

class AuthInterceptor(private val store: AuthStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { store.tokenFlow.firstOrNull().orEmpty() }
        val req = if (token.isNotBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(req)
    }
}

