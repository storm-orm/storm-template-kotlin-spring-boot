package com.example.app.model

import st.orm.DbTable
import st.orm.Entity
import st.orm.FK
import st.orm.PK
import java.time.LocalDate

/**
 * A user of the application, living in a city.
 *
 * The foreign key is the [City] itself, so reading a user reads its city in the same statement.
 * Declare it as `Ref<City>` instead when most reads do not need the city: the column stays in the
 * row, queries can still navigate and filter through it, and `fetch()` resolves it when needed.
 *
 * The table is named app_user because `user` is a reserved word on several databases.
 */
@DbTable("app_user")
data class User(
    @PK val id: Int = 0,
    val email: String,
    val birthDate: LocalDate?,
    val street: String,
    val postalCode: String,
    @FK val city: City
) : Entity<Int>
