package com.crosstime.backend.response

import java.util.UUID

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: UUID
)