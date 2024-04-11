package com.crosstime.backend.service.impl

import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.model.User
import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.service.UsersService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UsersServiceImpl(
    val usersRepository: UsersRepository,
    val usersMapper: UsersMapper
) : UsersService {

    override fun createUser(email: String, username: String): UUID? {
        val user = buildUser(email, username)
        val userEntity = usersMapper.mapToEntity(user)

        return usersRepository.save(userEntity).id
    }

    override fun getUserById(userId: UUID): User? {
        val userEntity = usersRepository.findById(userId)
        var user: UserModel? = null
        userEntity.ifPresent {
            user = usersMapper.mapToModel(it)
        }

        return user
    }

    private fun buildUser(email: String, username: String) = UserModel(username = username, email = email)

}