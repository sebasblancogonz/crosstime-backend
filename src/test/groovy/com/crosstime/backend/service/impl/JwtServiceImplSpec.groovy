package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.User as UserEntity
import spock.lang.Specification

class JwtServiceImplSpec extends Specification {

    private JwtServiceImpl jwtService

    def setup() {
        jwtService = new JwtServiceImpl("XYP8xCLLORE+23lE/2ELxIdh67nYCuxqPoudS5TLIsh4NhzZ6RnRtj1/wCIANDu+fILa/ipv3kaE3LohOha/0rfNSq9OmFAxwq9AYxqIkkaywjOe0cl6/whB", 3600000, 3600000)
    }

    def "class with null properties"() {
        when: "The class is instantiated with null properties"
        def jwtService = new JwtServiceImpl(null, 0, 0)

        then: "The properties are null"
        assert jwtService.secretKey == null
        assert jwtService.expiration == 0
        assert jwtService.refreshExpiration == 0
    }

    def "should return a jwt token"() {
        given: "A user entity"
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        when: "The method is called"
        def token = jwtService.generateToken(new HashMap(), userEntity)

        then: "The token is not null"
        assert token != null
    }

    def "should return a jwt token"() {
        given: "A user entity"
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        when: "The method is called"
        def token = jwtService.generateToken(userEntity)

        then: "The token is not null"
        assert token != null
    }

    def "should refresh a jwt token"() {
        given: "A user entity"
        def userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "test", Role.USER)

        when: "The method is called"
        def token = jwtService.generateRefreshToken(userEntity)

        then: "The token is not null"
        assert token != null
    }

}
