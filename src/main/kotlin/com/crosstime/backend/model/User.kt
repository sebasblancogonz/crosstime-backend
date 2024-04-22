package com.crosstime.backend.model

import com.crosstime.backend.entity.Role
import java.util.UUID

data class User(
        val id: UUID? = null,
        val username: String,
        val email: String,
        val password: String,
        val role: Role
)
