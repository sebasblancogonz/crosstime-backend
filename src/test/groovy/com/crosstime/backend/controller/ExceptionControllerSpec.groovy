package com.crosstime.backend.controller


import com.crosstime.backend.exeption.EmailAlreadyRegisteredException
import com.crosstime.backend.exeption.FullSlotException
import com.crosstime.backend.exeption.SlotAlreadyReservedException
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.exeption.UserNotSavedException
import spock.lang.Specification

class ExceptionControllerSpec extends Specification {

    private ExceptionController exceptionController

    def setup() {
        exceptionController = new ExceptionController()
    }

    def "should return a response entity with the exception message for a user not found with userId"() {
        given: "A user id"
        def userId = UUID.randomUUID()
        when: "The method is called"
        def responseEntity = exceptionController.handleUserNotFoundException(new UserNotFoundException(userId, null))

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "User with id $userId not found."
    }

    def "should return a response entity with the exception message for a user not found with email"() {
        given: "A user email"
        def email = "test@test.com"
        when: "The method is called"
        def responseEntity = exceptionController.handleUserNotFoundException(new UserNotFoundException(null, email))

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "User with email $email not found."
    }

    def "should return a response entity with the exception message for a user not found with email"() {
        when: "The method is called without email or userId"
        def responseEntity = exceptionController.handleUserNotFoundException(new UserNotFoundException(null, null))

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "User not found."
    }

    def "should return a response entity with the exception message for a user not saved"() {
        when: "The method is called"
        def responseEntity = exceptionController.handleUserNotSavedException(new UserNotSavedException())

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "The user could not be saved."
    }

    def "should return a response entity with the exception message for an email already registered"() {
        when: "The method is called"
        def responseEntity = exceptionController.handleEmailAlreadyRegisteredException(new EmailAlreadyRegisteredException("email@email.com"))

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "Email email@email.com is already registered."
    }

    def "should return a response entity with the exception message for a slot already reserved"() {
        when: "The method is called"
        def responseEntity = exceptionController.handleSlotAlreadyReservedException(new SlotAlreadyReservedException())

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "Slot cannot be reserved twice by the same user."
    }

    def "should return a response entity with the exception message for a not found slot"() {
        when: "The method is called"
        def responseEntity = exceptionController.handleSlotNotFoundException(new SlotNotFoundException())

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "Slot not found."
    }

    def "should return a response entity with the exception message for a full slot"() {
        when: "The method is called"
        def responseEntity = exceptionController.handleFullSlotException(new FullSlotException())

        then: "The response entity contains the exception message"
        assert responseEntity.body["message"] == "Could not create reservation. Slot is full."
    }

}