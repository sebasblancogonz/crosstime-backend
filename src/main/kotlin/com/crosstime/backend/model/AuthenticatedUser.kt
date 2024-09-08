package com.crosstime.backend.model

import com.crosstime.backend.entity.Role
import com.crosstime.backend.enums.UserType
import java.util.UUID

class AuthenticatedUser(
    val password: String,
    val role: Role,
    override val id: UUID? = null,
    override val username: String,
    override val email: String,
    val userType: UserType?
) : User(id, username, email)
