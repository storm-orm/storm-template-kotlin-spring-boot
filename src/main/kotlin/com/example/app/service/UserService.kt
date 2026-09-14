package com.example.app.service

import com.example.app.model.City
import com.example.app.model.User
import com.example.app.repository.CityRepository
import com.example.app.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * The application's one service. Storm's repositories join the Spring transaction the annotation
 * opens, so `@Transactional` is all the transaction management this needs.
 */
@Service
class UserService(
    private val userRepository: UserRepository,
    private val cityRepository: CityRepository
) {

    /** Everyone living in the named city. A read-only transaction; the database is told so. */
    @Transactional(readOnly = true)
    fun findInCity(cityName: String): List<User> = userRepository.findByCityName(cityName)

    /**
     * Registers a user, creating the city on first use. Both writes are in one transaction, so a
     * failure leaves neither behind.
     */
    @Transactional
    fun register(
        email: String,
        birthDate: LocalDate?,
        street: String,
        postalCode: String,
        cityName: String
    ): User {
        val city = cityRepository.findByName(cityName) ?: cityRepository.insertAndFetch(City(name = cityName))
        return userRepository.insertAndFetch(
            User(email = email, birthDate = birthDate, street = street, postalCode = postalCode, city = city)
        )
    }
}
