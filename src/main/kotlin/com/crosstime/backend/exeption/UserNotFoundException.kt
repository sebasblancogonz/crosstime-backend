package com.crosstime.backend.exeption

import java.util.UUID

class UserNotFoundException(
    private val userId: UUID? = null,
    val email: String? = null
) : RuntimeException() {
    override val message: String
        get() = userId?.let {
            "User with id $userId not found."
        } ?: email?.let {
            "User with email $email not found."
        } ?: "User not found."

}