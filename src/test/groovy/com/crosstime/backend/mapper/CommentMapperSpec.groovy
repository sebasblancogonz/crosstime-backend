package com.crosstime.backend.mapper

import com.crosstime.backend.entity.Comment as CommentEntity
import com.crosstime.backend.entity.Post as PostEntity
import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.enums.UserType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.Instant

@SpringBootTest
class CommentMapperSpec extends Specification {

    @Autowired
    CommentMapper commentMapper

    UUID mockUuid = UUID.randomUUID()

    def "should map a comment entity to a model"() {
        given: "An comment entity"
        def uuid = UUID.randomUUID()
        def commentEntity = buildCommentEntity()
        when: "The entity is mapped to a model"
        def commentModel = commentMapper.mapToModel(commentEntity)
        then: "The model has the same values as the entity"
        assert commentModel.content == commentEntity.content
        assert commentModel.user.id == commentEntity.user.id
    }

    def buildCommentEntity() {
        return new CommentEntity(
                mockUuid,
                "content of the comment",
                Date.from(Instant.parse("2018-11-30T18:35:24.00Z")),
                buildUserEntity(),
                buildPostEntity(),
                Collections.emptyList()
        )
    }

    def buildUserEntity() {
        return new UserEntity(
                mockUuid,
                "alias",
                "email@email.com",
                "password",
                Role.USER,
                UserType.ATHLETE
        )
    }

    def buildPostEntity() {
        return new PostEntity(
                mockUuid,
                "Content of the post",
                Date.from(Instant.parse("2018-11-30T18:35:24.00Z")),
                buildUserEntity(),
                Collections.emptyList(),
                Collections.emptyList()
        )
    }

}
