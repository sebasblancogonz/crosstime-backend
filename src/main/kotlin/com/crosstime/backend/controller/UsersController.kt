package com.crosstime.backend.controller

import com.crosstime.backend.model.User
import com.crosstime.backend.request.UserRequest
import com.crosstime.backend.service.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController(value = "/api/users")
class UsersController(
    val usersService: UsersService
) {

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<UUID> {
        return ResponseEntity.ok(usersService.createUser(request.email, request.username));
    }

}