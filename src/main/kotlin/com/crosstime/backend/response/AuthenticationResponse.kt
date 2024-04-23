package com.crosstime.backend.response

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)