package com.crosstime.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrosstimeBackendApplication

fun main(args: Array<String>) {
    runApplication<CrosstimeBackendApplication>(*args)
}
