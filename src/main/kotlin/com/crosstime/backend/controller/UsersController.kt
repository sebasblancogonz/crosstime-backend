package com.crosstime.backend.controller

import com.crosstime.backend.model.User
import com.crosstime.backend.request.UserRequest
import com.crosstime.backend.response.UserResponse
import com.crosstime.backend.service.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UsersController(
    val usersService: UsersService
) {

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<Any> {
        val userId = usersService.createUser(request.email, request.username)
        return ResponseEntity.ok(UserResponse(userId));
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: String): ResponseEntity<Any> =
        ResponseEntity.ok(usersService.getUserById(UUID.fromString(userId)))


    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> = ResponseEntity.ok(usersService.findAllUsers())

}