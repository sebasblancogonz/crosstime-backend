package com.crosstime.backend.controller

import com.crosstime.backend.model.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UsersController {

    @GetMapping
    fun getUsers(): User {
        return User(id = UUID.randomUUID(), "Username", "email@email.com", "password")
    }

}