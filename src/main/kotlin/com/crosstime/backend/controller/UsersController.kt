package com.crosstime.backend.controller

import com.crosstime.backend.model.User
import com.crosstime.backend.service.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "/api/users")
class UsersController(
    val usersService: UsersService
) {

    @PostMapping
    fun createUser(): ResponseEntity<User> {
        return ResponseEntity.ok(usersService.createUser());
    }

}