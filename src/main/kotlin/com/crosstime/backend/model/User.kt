package com.crosstime.backend.model

import java.util.UUID

open class User(
    open val id: UUID? = null,
    open val username: String,
    open val email: String
)