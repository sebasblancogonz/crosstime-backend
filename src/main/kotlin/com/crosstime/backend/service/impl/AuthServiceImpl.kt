package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Token
import com.crosstime.backend.enums.TokenType
import com.crosstime.backend.exeption.EmailAlreadyRegisteredException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.repository.TokenRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.AuthenticationRequest
import com.crosstime.backend.request.RegisterRequest
import com.crosstime.backend.response.AuthenticationResponse
import com.crosstime.backend.service.AuthService
import com.crosstime.backend.service.JwtService
import com.crosstime.backend.service.LogoutService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import com.crosstime.backend.entity.User as UserEntity

@Service
class AuthServiceImpl(
    private val usersRepository: UsersRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val logoutService: LogoutService,
    private val usersMapper: UsersMapper,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AuthService {

    @Transactional(readOnly = false)
    override fun register(request: RegisterRequest): AuthenticationResponse {
        val user = RegisterRequest.toModel(request, passwordEncoder)
        checkIfUserExists(user.email)

        val userEntity: UserEntity = usersMapper.mapToEntity(user)
        val savedUser = usersRepository.save(userEntity)

        val token = jwtService.generateToken(userEntity)
        val refreshToken = jwtService.generateRefreshToken(userEntity)
        saveUserToken(token, savedUser)

        return AuthenticationResponse(accessToken = token, refreshToken = refreshToken)
    }

    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user =
            checkNotNull(usersRepository.findByEmail(request.email))
            { throw UserNotFoundException(email = request.email) }
        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        revokeAllUserTokens(user)
        saveUserToken(jwtToken, user)
        return AuthenticationResponse(accessToken = jwtToken, refreshToken = refreshToken)
    }

    @Throws(IOException::class)
    override fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }

        val refreshToken = authHeader.substring(7)
        val userEmail = jwtService.extractUsername(refreshToken)
        val user =
            checkNotNull(usersRepository.findByEmail(userEmail)) { throw UserNotFoundException(email = userEmail) }

        if (jwtService.isTokenValid(refreshToken, user)) {
            val token = jwtService.generateToken(user)
            revokeAllUserTokens(user)
            saveUserToken(token, user)
            val authResponse = AuthenticationResponse(accessToken = token, refreshToken = refreshToken)
            ObjectMapper().writeValue(response.outputStream, authResponse)
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

    override fun logout(request: HttpServletRequest, response: HttpServletResponse) {
        logoutService.logout(request, response, null)
    }

    private fun saveUserToken(token: String, user: UserEntity) =
        tokenRepository.save(
            Token(
                token = token,
                user = user,
                tokenType = TokenType.BEARER,
                revoked = false,
                expired = false
            )
        )

    private fun revokeAllUserTokens(user: UserEntity) {
        val validUserTokens = tokenRepository.findAllValidTokensByUserId(user.id!!)
        if (validUserTokens.isEmpty()) return
        validUserTokens.map {
            it.revokeUserTokens()
        }

        tokenRepository.saveAll(validUserTokens)
    }

    private fun checkIfUserExists(email: String) {
        if (usersRepository.findByEmail(email) != null) {
            throw EmailAlreadyRegisteredException(email)
        }
    }
}