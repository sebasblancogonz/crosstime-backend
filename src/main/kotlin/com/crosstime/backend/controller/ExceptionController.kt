package com.crosstime.backend.controller

import com.crosstime.backend.exeption.EmailAlreadyRegisteredException
import com.crosstime.backend.exeption.SlotAlreadyReservedException
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.exeption.UserNotSavedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(UserNotSavedException::class)
    fun handleUserNotSavedException(exception: UserNotSavedException): ResponseEntity<Map<String, String>> {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.message);
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<Map<String, String>> {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.message);
    }

    @ExceptionHandler(SlotNotFoundException::class)
    fun handleSlotNotFoundException(exception: SlotNotFoundException): ResponseEntity<Map<String, String>> {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.message);
    }

    @ExceptionHandler(SlotAlreadyReservedException::class)
    fun handleSlotAlreadyReservedException(exception: SlotAlreadyReservedException): ResponseEntity<Map<String, String>> {
        return buildResponseEntity(HttpStatus.FORBIDDEN, exception.message);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException::class)
    fun handleEmailAlreadyRegisteredException(exception: EmailAlreadyRegisteredException): ResponseEntity<Map<String, String>> {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.message);
    }

    private fun buildResponseEntity(status: HttpStatus, message: String): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(status).body(mapOf("message" to message));
    }

}