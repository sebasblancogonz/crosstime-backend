package com.crosstime.backend.model

import java.util.UUID

data class User(
        val id: UUID? = null,
        val username: String,
        val email: String
)
