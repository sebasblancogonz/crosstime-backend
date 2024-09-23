package com.crosstime.backend.configuration

import com.crosstime.backend.repository.TokenRepository
import com.crosstime.backend.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val tokenRepository: TokenRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            if (request.servletPath.contains("/api/v1/auth")) {
                filterChain.doFilter(request, response)
                return
            }
            val authHeader = request.getHeader("Authorization")
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }
            val jwt: String = authHeader.substring(7)
            val userEmail: String = jwtService.extractUsername(jwt)

            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(userEmail)
                val token = tokenRepository.findByToken(jwt)
                val isTokenValid: Boolean = token != null && !token.expired && !token.revoked
                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
            filterChain.doFilter(request, response)
        } catch (exception: ExpiredJwtException) {
            handleExpiredJwtException(response, exception)
        }
    }

    fun handleExpiredJwtException(response: HttpServletResponse, expiredJwtException: ExpiredJwtException) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.writer.write("{ \"message\": \"Token expired, please log in again.\" }")
    }

}