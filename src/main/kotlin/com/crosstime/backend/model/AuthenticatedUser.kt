package com.crosstime.backend.model

import com.crosstime.backend.entity.Role
import java.util.*

class AuthenticatedUser(
    val password: String,
    val role: Role,
    id: UUID? = null,
    username: String,
    email: String
) : User(id, username, email)
