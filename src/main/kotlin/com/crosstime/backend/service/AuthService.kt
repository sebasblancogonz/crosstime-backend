package com.crosstime.backend.service

import com.crosstime.backend.request.AuthenticationRequest
import com.crosstime.backend.request.RegisterRequest
import com.crosstime.backend.response.AuthenticationResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface AuthService {

    fun register(request: RegisterRequest): AuthenticationResponse

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse

    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse)

    fun logout(request: HttpServletRequest, response: HttpServletResponse)

}