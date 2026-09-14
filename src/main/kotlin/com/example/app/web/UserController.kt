package com.example.app.web

import com.example.app.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun users(@RequestParam city: String): List<UserResponse> =
        userService.findInCity(city).map { it.toResponse() }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: CreateUserRequest): UserResponse =
        userService.register(
            email = request.email,
            birthDate = request.birthDate?.let(LocalDate::parse),
            street = request.street,
            postalCode = request.postalCode,
            cityName = request.city
        ).toResponse()
}
