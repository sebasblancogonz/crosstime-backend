package com.crosstime.backend.repository

import com.crosstime.backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UsersRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String): User?

}
