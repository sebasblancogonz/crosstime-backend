package com.crosstime.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tokens")
class Token(
    @Column(name = "token", unique = true) var token: String?,
    @Enumerated(EnumType.STRING) var tokenType: TokenType = TokenType.BEARER,
    var revoked: Boolean = false,
    var expired: Boolean = false,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun logout() {
        expired = true
        revoked = false
    }

    fun revokeUserTokens() {
        expired = true
        revoked = true
    }

}