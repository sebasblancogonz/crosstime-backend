package com.crosstime.backend.service

import com.crosstime.backend.model.User
import java.util.UUID

interface UsersService {

    fun createUser(email: String, username: String): UUID
    fun getUserById(userId: UUID): User?
    fun findAllUsers(): List<User>

}