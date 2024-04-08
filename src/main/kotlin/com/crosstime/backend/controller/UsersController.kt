package com.crosstime.backend.controller

import com.crosstime.backend.model.User
import com.crosstime.backend.service.UsersService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(
    val usersService: UsersService
) {

    @GetMapping
    fun getUsers(): User {
        return usersService.createUser()
    }

}