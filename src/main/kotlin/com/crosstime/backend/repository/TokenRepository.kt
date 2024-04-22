package com.crosstime.backend.repository

import com.crosstime.backend.entity.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface TokenRepository: JpaRepository<Token, Long> {

    @Query(
        value = """
            select t from Token t inner join User u
            on t.user.id = u.id where u.id = :userId 
            and (t.expired = false or t.revoked = false)
            """
    )
    fun findAllValidTokensByUserId(userId: UUID): List<Token>

    fun findByToken(token: String): Token?
}