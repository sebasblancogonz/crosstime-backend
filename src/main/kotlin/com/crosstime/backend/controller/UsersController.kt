package com.crosstime.backend.controller

import com.crosstime.backend.request.UserRequest
import com.crosstime.backend.response.UserResponse
import com.crosstime.backend.service.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UsersController(
    val usersService: UsersService
) {

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<Any> {
        val userId = usersService.createUser(request.email, request.username)
        return userId?.let { ResponseEntity.ok(UserResponse(userId)) }
            ?: run {
                ResponseEntity.of(
                    ProblemDetail.forStatusAndDetail(
                        HttpStatusCode.valueOf(500),
                        "The user could not be created"
                    )
                ).build()
            };
    }

}