package com.crosstime.backend.exeption

import java.util.UUID

class UserNotFoundException(
    val userId: UUID
) : RuntimeException() {
    override val message: String
        get() = "User with id $userId not found."
}