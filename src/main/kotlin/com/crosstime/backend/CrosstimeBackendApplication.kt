package com.crosstime.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("com.crosstime.backend.*")
@EnableJpaRepositories("com.crosstime.backend.*")
class CrosstimeBackendApplication

fun main(args: Array<String>) {
    runApplication<CrosstimeBackendApplication>(*args)
}
