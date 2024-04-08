package com.crosstime.backend.service.impl

import com.crosstime.backend.model.User
import com.crosstime.backend.service.UsersService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersServiceImpl : UsersService {
    override fun createUser(): User {
        return User(id = UUID.randomUUID(), "Username", "email@email.com", "password")
    }
}