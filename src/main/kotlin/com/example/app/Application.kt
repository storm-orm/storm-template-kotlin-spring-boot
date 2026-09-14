package com.example.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The starter creates the ORMTemplate from the DataSource, registers every repository interface as
 * a bean, bridges Storm's transactions with Spring's, and validates every entity against the live
 * schema at startup, after Flyway has migrated it.
 */
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
