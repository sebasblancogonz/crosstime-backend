package com.crosstime.backend.exeption

class EmailAlreadyRegisteredException(
    val email: String
) : RuntimeException() {
    override val message: String
        get() = "Email $email is already registered."
}