package com.crosstime.backend.service

import java.util.UUID

interface UsersService {

    fun createUser(email: String, username: String): UUID

}