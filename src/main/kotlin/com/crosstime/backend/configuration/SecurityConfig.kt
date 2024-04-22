package com.crosstime.backend.configuration

import com.crosstime.backend.entity.Permission
import com.crosstime.backend.entity.Role
import com.crosstime.backend.service.LogoutService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val logoutService: LogoutService,
    private val authenticationProvider: AuthenticationProvider,
    private val jwtAuthFilter: JwtAuthenticationFilter
) {

    //generate a static constant with /api/v1/auth/** as value
    companion object {
        const val AUTH_URL = "/api/v1/auth/**"
        const val ADMIN_URL = "/api/v1/admin/**"
        const val LOGOUT_URL = "/api/v1/auth/logout"
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers(
                    AUTH_URL,
                )
                    .permitAll()
                    .requestMatchers(ADMIN_URL).hasRole(Role.ADMIN.name)
                    .requestMatchers(GET, ADMIN_URL).hasAuthority(Permission.ADMIN_READ.name)
                    .requestMatchers(POST, ADMIN_URL).hasAuthority(Permission.ADMIN_CREATE.name)
                    .requestMatchers(PUT, ADMIN_URL).hasAuthority(Permission.ADMIN_UPDATE.name)
                    .requestMatchers(DELETE, ADMIN_URL).hasAuthority(Permission.ADMIN_DELETE.name)
                    .anyRequest()
                    .authenticated()
            }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            }

            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout {
                it.logoutUrl(LOGOUT_URL)
                    .addLogoutHandler(logoutService)
                    .logoutSuccessHandler { _: HttpServletRequest?,
                                            _: HttpServletResponse?,
                                            _: Authentication? ->
                        SecurityContextHolder.clearContext()
                    }
            }



        return http.build()
    }

}