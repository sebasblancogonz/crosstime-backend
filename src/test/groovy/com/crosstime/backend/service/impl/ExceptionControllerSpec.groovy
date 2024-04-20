package com.crosstime.backend.service.impl

import com.crosstime.backend.controller.ExceptionController
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.exeption.UserNotSavedException
import spock.lang.Specification

class ExceptionControllerSpec extends Specification {

    private ExceptionController exceptionController

    def setup() {
        exceptionController = new ExceptionController()
    }

    def "should return a response entity with the exception message for a user not found"() {
        given: "A user id"
        def userId = UUID.randomUUID()
        when: "The method is called"
        def responseEntity = exceptionController.handleUserNotFoundException(new UserNotFoundException(userId))

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "User with id $userId not found."
    }

    def "should return a response entity with the exception message for a user not saved"() {
        when: "The method is called"
        def responseEntity = exceptionController.handleUserNotSavedException(new UserNotSavedException())

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "The user could not be saved."
    }

}