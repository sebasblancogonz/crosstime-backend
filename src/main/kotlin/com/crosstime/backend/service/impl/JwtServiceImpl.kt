package com.crosstime.backend.service.impl

import com.crosstime.backend.service.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import java.util.function.Function

@Service
class JwtServiceImpl(
    @Value("\${application.security.jwt.secret-key}")
    private val secretKey: String? = null,
    @Value("\${application.security.jwt.expiration}")
    private val expiration: Long = 0,
    @Value("\${application.security.jwt.refresh-token.expiration}")
    private val refreshExpiration: Long = 0
) : JwtService {

    override fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    override fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    override fun generateToken(userDetails: UserDetails): String =
        generateToken(HashMap(), userDetails)


    override fun generateToken(extraClaims: Map<String?, Any>, userDetails: UserDetails): String =
        buildToken(extraClaims, userDetails, expiration)

    override fun generateRefreshToken(userDetails: UserDetails): String =
        buildToken(HashMap(), userDetails, refreshExpiration)

    override fun isTokenValid(token: String?, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String?): Date = extractClaim(token) { obj: Claims -> obj.expiration }

    private fun buildToken(
        extraClaims: Map<String?, Any?>,
        userDetails: UserDetails,
        expiration: Long
    ): String = Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.username)
        .signWith(signInKey)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + expiration))
        .signWith(signInKey, SignatureAlgorithm.HS256)
        .compact()

    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private val signInKey: Key
        get() {
            val keyBytes = Decoders.BASE64.decode(secretKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }

}