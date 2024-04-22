package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Role
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.exeption.UserNotSavedException
import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.model.User
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.service.UsersService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.jvm.optionals.getOrElse

@Service
class UsersServiceImpl(
    val usersRepository: UsersRepository,
    val usersMapper: UsersMapper
) : UsersService, UserDetailsService {

    override fun createUser(email: String, username: String): UUID {
        val user = buildUser(email, username)
        val userEntity = usersMapper.mapToEntity(user)
        return usersRepository.save(userEntity).id ?: throw UserNotSavedException()
    }

    override fun getUserById(userId: UUID): User? {
        val userEntity = usersRepository.findById(userId)
        return usersMapper.mapToModel(userEntity.getOrElse { throw UserNotFoundException(userId) })
    }

    override fun findAllUsers(): List<User> {
        val userEntities = usersRepository.findAll()
        userEntities.ifEmpty {
            return emptyList()
        }
        return usersMapper.mapToModelList(userEntities)
    }

    private fun buildUser(email: String, username: String) =
        User(username = username, email = email, password = "", role = Role.USER)

    override fun loadUserByUsername(username: String): UserDetails =
        checkNotNull(usersRepository.findByEmail(username)) { throw UserNotFoundException() }

}