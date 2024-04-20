package com.crosstime.backend.exeption

class UserNotSavedException : RuntimeException() {

    override val message: String
        get() = "The user could not be saved."

}