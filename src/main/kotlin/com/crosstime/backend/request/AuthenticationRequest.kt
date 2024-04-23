package com.crosstime.backend.request

data class AuthenticationRequest(
    val email: String,
    val password: String
)