package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.Token
import com.crosstime.backend.entity.TokenType
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.exeption.EmailAlreadyRegisteredException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.repository.TokenRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.AuthenticationRequest
import com.crosstime.backend.request.RegisterRequest
import com.crosstime.backend.service.JwtService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletOutputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.lang.reflect.UndeclaredThrowableException

class AuthServiceImplSpec extends Specification {

    def usersRepository = Mock(UsersRepository)
    def tokenRepository = Mock(TokenRepository)
    def passwordEncoder = Mock(PasswordEncoder)
    def jwtService = Mock(JwtService)
    def usersMapper = Mock(UsersMapper)
    def authenticationManager = Mock(AuthenticationManager)

    AuthServiceImpl authService

    def setup() {
        authService = new AuthServiceImpl(
                usersRepository,
                tokenRepository,
                passwordEncoder,
                usersMapper,
                jwtService,
                authenticationManager
        )
    }

    def "should register a new user"() {
        given: "Register request"
        def request = new RegisterRequest("test", "test", "test@test.com", Role.USER)
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        and: "the password is encoded"
        1 * passwordEncoder.encode(request.password) >> "test"

        and: "the user does not exist in the database"
        1 * usersRepository.findByEmail(request.email) >> null

        and: "the user request is mapped to a user entity"
        1 * usersMapper.mapToEntity(_) >> userEntity

        and: "the user entity is saved in the database"
        1 * usersRepository.save(_) >> userEntity

        and: "the token and the refresh token are generated"
        1 * jwtService.generateToken(_) >> "token"
        1 * jwtService.generateRefreshToken(_) >> "refreshToken"

        and: "the token is saved in the database"
        1 * tokenRepository.save(_) >> new Token("token", TokenType.BEARER, false, false, userEntity)

        when: "The method is called"
        def response = authService.register(request)

        then: "The response contains the token and the refresh token"
        assert response.accessToken == "token"
        assert response.refreshToken == "refreshToken"
    }

    def "should not register a user with an already registered email"() {
        given: "Register request"
        def request = new RegisterRequest("test", "test", "test@test.com", Role.USER)
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        and: "the password is encoded"
        1 * passwordEncoder.encode(request.password) >> "test"

        and: "the user exists in the database"
        1 * usersRepository.findByEmail(request.email) >> userEntity

        when: "The method is called"
        authService.register(request)

        then: "An EmailAlreadyRegisteredException is thrown"
        thrown(EmailAlreadyRegisteredException)
    }

    def "should authenticate a user"() {
        given: "an authentication request"
        def authenticationRequest = new AuthenticationRequest("test@test.com", "test")
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        and: "the authentication manager authenticates the user"
        1 * authenticationManager.authenticate(_)

        and: "the user exists in the database"
        1 * usersRepository.findByEmail(authenticationRequest.email) >> userEntity

        and: "the token and the refresh token are generated"
        1 * jwtService.generateToken(userEntity) >> "token"
        1 * jwtService.generateRefreshToken(userEntity) >> "refreshToken"

        and: "the valid tokens are retrieved from the database"
        1 * tokenRepository.findAllValidTokensByUserId(userEntity.id) >> [new Token("token", TokenType.BEARER, false, false, userEntity)]

        and: "the token is saved in the database"
        1 * tokenRepository.saveAll(_) >> [new Token("token", TokenType.BEARER, true, true, userEntity)]

        and: "the new token is saved in the database"
        1 * tokenRepository.save(_) >> new Token("token", TokenType.BEARER, false, false, userEntity)

        when: "The method is called"
        def response = authService.authenticate(authenticationRequest)

        then: "The response contains the token and the refresh token"
        assert response.accessToken == "token"
        assert response.refreshToken == "refreshToken"
    }

    def "should authenticate a user"() {
        given: "an authentication request"
        def authenticationRequest = new AuthenticationRequest("test@test.com", "test")
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        and: "the user exists in the database"
        1 * usersRepository.findByEmail(authenticationRequest.email) >> null

        when: "The method is called"
        authService.authenticate(authenticationRequest)

        then: "The response contains the token and the refresh token"
        def exception = thrown(UserNotFoundException)
        assert exception.message == "User with email test@test.com not found."
    }

    def "should refresh a token"() {
        given: "an httpServletRequest"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)
        def objectMapper = Mock(ObjectMapper)

        and: "the token is retrieved from the request"
        1 * httpServletRequest.getHeader("Authorization") >> "Bearer token"

        and: "the username is retrieved from the token"
        1 * jwtService.extractUsername("token") >> "email@email.com"

        and: "the user exists in the database"
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)
        1 * usersRepository.findByEmail("email@email.com") >> userEntity

        and: "the token is a valid one"
        1 * jwtService.isTokenValid("token", userEntity) >> true

        and: "the new token is generated"
        1 * jwtService.generateToken(userEntity) >> "newToken"

        and: "the previous tokens are invalidated"
        1 * tokenRepository.findAllValidTokensByUserId(userEntity.id) >> [new Token("token", TokenType.BEARER, false, false, userEntity)]

        and: "the tokens are saved in the database"
        1 * tokenRepository.saveAll(_) >> [new Token("token", TokenType.BEARER, true, true, userEntity)]

        and: "the new token is saved in the database"
        1 * tokenRepository.save(_) >> new Token("newToken", TokenType.BEARER, false, false, userEntity)

        and: "the response is written to the response"
        def servletOutputStream = Mock(ServletOutputStream)
        1 * httpServletResponse.getOutputStream() >> servletOutputStream
        1 * servletOutputStream.write(*_) >> null
        1 * servletOutputStream.close() >> null

        when: "The method is called"
        authService.refreshToken(httpServletRequest, httpServletResponse)

        then: "The response contains the new token"
        _ * objectMapper._
    }

    def "should send an error if the refresh token is not valid"() {
        given: "an httpServletRequest"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)

        and: "the token is retrieved from the request"
        1 * httpServletRequest.getHeader("Authorization") >> "Bearer token"

        and: "the username is retrieved from the token"
        1 * jwtService.extractUsername("token") >> "email@email.com"

        and: "the user exists in the database"
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)
        1 * usersRepository.findByEmail("email@email.com") >> userEntity

        and: "the token is a not a valid one"
        1 * jwtService.isTokenValid("token", userEntity) >> false

        when: "The method is called"
        authService.refreshToken(httpServletRequest, httpServletResponse)

        then: "response should send an error"
        1 * httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }

    def "should send an error if the refresh token is null or has incorrect format"(String description, String token) {
        given: "an httpServletRequest"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)

        and: "the token is retrieved from the request"
        1 * httpServletRequest.getHeader("Authorization") >> token

        when: "The method is called"
        authService.refreshToken(httpServletRequest, httpServletResponse)

        then: "response should send an error"
        1 * httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)

        where:
        description    | token
        "is not valid" | "token"
        "is null"      | null
    }

    def "should throw an exception if the user does not exist"() {
        given: "an httpServletRequest"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)

        and: "the token is retrieved from the request"
        1 * httpServletRequest.getHeader("Authorization") >> "Bearer token"

        and: "the username is retrieved from the token"
        1 * jwtService.extractUsername("token") >> "email@email.com"

        and: "the user exists in the database"
        1 * usersRepository.findByEmail("email@email.com") >> null

        when: "The method is called"
        authService.refreshToken(httpServletRequest, httpServletResponse)

        then: "response should send an error"
        thrown(UserNotFoundException)
    }

    def "should throw an exception when extracting the headers"() {
        given: "an httpServletRequest"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)

        and: "the token is retrieved from the request"
        1 * httpServletRequest.getHeader("Authorization") >> { throw new IOException("error") }

        when: "The method is called"
        authService.refreshToken(httpServletRequest, httpServletResponse)

        then: "response should send an error"
        def exception = thrown(UndeclaredThrowableException)
        assert exception.cause instanceof IOException
        assert exception.cause.message == "error"
    }

}