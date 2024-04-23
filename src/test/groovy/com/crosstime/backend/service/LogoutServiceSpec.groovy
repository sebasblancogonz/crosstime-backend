package com.crosstime.backend.service

import com.crosstime.backend.entity.Token
import com.crosstime.backend.entity.TokenType
import com.crosstime.backend.entity.User
import com.crosstime.backend.repository.TokenRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import spock.lang.Specification
import spock.lang.Unroll

class LogoutServiceSpec extends Specification {

    def tokenRepository = Mock(TokenRepository)

    LogoutService logoutService

    def setup() {
        logoutService = new LogoutService(tokenRepository)
    }

    def "should logout the user"() {
        given: "an httpServletRequest, a httpServletResponse and an authentification"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)
        def authentication = Mock(Authentication)
        def user = Mock(User)
        def inactiveToken = new Token("token", TokenType.BEARER, false, true, user)
        def token = new Token("token", TokenType.BEARER, false, false, user)

        when: "the method is called to logout the user"
        logoutService.logout(httpServletRequest, httpServletResponse, authentication)

        then: "the bearer token obtained from the request"
        1 * httpServletRequest.getHeader("Authorization") >> "Bearer token"

        and: "the token is retrieved from the repository"
        1 * tokenRepository.findByToken("token") >> token

        and: "the token is updated to be inactive"
        1 * tokenRepository.save(_) >> inactiveToken
    }

    @Unroll
    def "shouldn't do anything #description"(String token) {
        given: "an httpServletRequest, a httpServletResponse and an authentification"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)
        def authentication = Mock(Authentication)

        when: "the method is called to logout the user"
        logoutService.logout(httpServletRequest, httpServletResponse, authentication)

        then: "the bearer token obtained from the request"
        1 * httpServletRequest.getHeader("Authorization") >> "invalidtoken"

        and: "the token is retrieved from the repository"
        0 * tokenRepository.findByToken("token")

        and: "the token is updated to be inactive"
        0 * tokenRepository.save(_)

        where:
        description             | token
        "with an invalid token" | "invalidtoken"
        "with an empty token"   | ""
        "with a null token"     | null
    }

    def "shouldn't do anything when there is no token"() {
        given: "an httpServletRequest, a httpServletResponse and an authentification"
        def httpServletRequest = Mock(HttpServletRequest)
        def httpServletResponse = Mock(HttpServletResponse)
        def authentication = Mock(Authentication)

        when: "the method is called to logout the user"
        logoutService.logout(httpServletRequest, httpServletResponse, authentication)

        then: "the bearer token obtained from the request"
        1 * httpServletRequest.getHeader("Authorization") >> "Bearer token"

        and: "the token is retrieved from the repository"
        1 * tokenRepository.findByToken("token") >> null

        and: "the token is updated to be inactive"
        0 * tokenRepository.save(_)
    }

}
