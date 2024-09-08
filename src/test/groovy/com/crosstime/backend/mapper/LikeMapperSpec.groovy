package com.crosstime.backend.mapper

import com.crosstime.backend.entity.Comment
import com.crosstime.backend.entity.Like as LikeEntity
import com.crosstime.backend.entity.Post
import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.User
import com.crosstime.backend.enums.UserType
import com.crosstime.backend.model.Like as LikeModel
import com.crosstime.backend.model.Post as PostModel
import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.model.Comment as CommentModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LikeMapperSpec extends Specification {

    @Autowired
    LikeMapper likeMapper

    def "should map a like entity to a model"() {
        given: "A like entity"
        def likeEntity = buildLikeEntity()
        when: "The entity is mapped to a model"
        def likeModel = likeMapper.mapToModel(likeEntity)
        then: "The model has the same values as the entity"
        assert likeEntity.id == likeModel.id
        assert likeEntity.user.id == likeModel.user.id
        assert likeEntity.post.id == likeModel.post.id
    }

    def "should map a like model to an entity"() {
        given: "A like model"
        def likeModel = buildLikeModel()
        when: "The model is mapped to an entity"
        def likeEntity = likeMapper.mapToEntity(likeModel)
        then: "The entity has the same values as the model"
        assert likeModel.id == likeEntity.id
        assert likeModel.user.id == likeEntity.user.id
        assert likeModel.post.id == likeEntity.post.id
    }

    def "should map a list of like entities to a list of like models"() {
        given: "A list of like entities"
        def likeEntities = [buildLikeEntity(), buildLikeEntity()]
        when: "The entities are mapped to models"
        def likeModels = likeMapper.mapToModelList(likeEntities)
        then: "The models have the same values as the entities"
        assert likeEntities.size() == likeModels.size()
        assert likeEntities[0].id == likeModels[0].id
        assert likeEntities[0].user.id == likeModels[0].user.id
        assert likeEntities[0].post.id == likeModels[0].post.id
        assert likeEntities[1].id == likeModels[1].id
        assert likeEntities[1].user.id == likeModels[1].user.id
        assert likeEntities[1].post.id == likeModels[1].post.id
    }

    def "should map a list of like models to a list of like entities"() {
        given: "A list of like models"
        def likeModels = [buildLikeModel(), buildLikeModel()]
        when: "The models are mapped to entities"
        def likeEntities = likeMapper.mapToEntityList(likeModels)
        then: "The entities have the same values as the models"
        assert likeModels.size() == likeEntities.size()
        assert likeModels[0].id == likeEntities[0].id
        assert likeModels[0].user.id == likeEntities[0].user.id
        assert likeModels[0].post.id == likeEntities[0].post.id
        assert likeModels[1].id == likeEntities[1].id
        assert likeModels[1].user.id == likeEntities[1].user.id
        assert likeModels[1].post.id == likeEntities[1].post.id
    }

    def buildLikeEntity() {
        def user = new User(UUID.randomUUID(), "username", "email", "password", Role.USER, UserType.ATHLETE)
        def post = new Post(UUID.randomUUID(), "content", new Date(), user, new ArrayList<Comment>(), new ArrayList<LikeEntity>())
        return new LikeEntity(UUID.randomUUID(), user, post, null)
    }

    def buildLikeModel() {
        def user = new UserModel(UUID.randomUUID(), "username", "email")
        def post = new PostModel(UUID.randomUUID(), "content", new Date(), user, new ArrayList<CommentModel>(), new ArrayList<LikeModel>())
        return new LikeModel(UUID.randomUUID(), user, post, null)
    }
}
