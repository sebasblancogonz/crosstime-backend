package com.crosstime.backend.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: UUID,
    val username: String,
    val email: String
)
