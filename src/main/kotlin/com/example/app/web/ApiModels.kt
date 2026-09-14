package com.example.app.web

import com.example.app.model.User

/** What the API returns. A response type of its own keeps the wire format free of the table shape. */
data class UserResponse(
    val id: Int,
    val email: String,
    val birthDate: String?,
    val street: String,
    val postalCode: String,
    val city: String
)

fun User.toResponse() = UserResponse(
    id = id,
    email = email,
    birthDate = birthDate?.toString(),
    street = street,
    postalCode = postalCode,
    city = city.name
)

/** What the API accepts. */
data class CreateUserRequest(
    val email: String,
    val birthDate: String? = null,
    val street: String,
    val postalCode: String,
    val city: String
)
