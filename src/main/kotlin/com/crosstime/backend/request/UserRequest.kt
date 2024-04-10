package com.crosstime.backend.request

import jakarta.validation.constraints.NotEmpty

data class UserRequest(
    @NotEmpty(message = "The username cannot be empty")
    val username: String,
    @NotEmpty(message = "The email cannot be empty")
    val email: String
)
