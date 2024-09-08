package com.crosstime.backend.request

import com.crosstime.backend.entity.Role
import com.crosstime.backend.enums.UserType
import com.crosstime.backend.model.AuthenticatedUser
import com.crosstime.backend.model.User
import org.springframework.security.crypto.password.PasswordEncoder

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val role: Role,
    val userType: UserType?
) {
    companion object {
        fun toModel(request: RegisterRequest, passwordEncoder: PasswordEncoder): AuthenticatedUser = AuthenticatedUser(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            role = request.role,
            userType = request.userType?: UserType.ATHLETE
        )

    }
}
