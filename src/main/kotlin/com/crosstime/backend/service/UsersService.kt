package com.crosstime.backend.service

import com.crosstime.backend.model.User
import java.util.UUID

interface UsersService {

    fun getUserById(userId: UUID): User?
    fun findAllUsers(): List<User>

}