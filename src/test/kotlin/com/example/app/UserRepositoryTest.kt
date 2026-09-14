package com.example.app

import com.example.app.model.User
import com.example.app.repository.CityRepository
import com.example.app.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import st.orm.repository.repository
import st.orm.template.ORMTemplate
import st.orm.test.CapturedSql.Operation
import st.orm.test.SqlCapture
import st.orm.test.StormTest
import java.time.LocalDate

/**
 * Repository tests run on H2, on a database of its own per test class, created from the migration
 * and rolled back after each test. The test asks for what it needs as parameters: an ORMTemplate,
 * and a SqlCapture to assert on the statements Storm generates.
 */
@StormTest(scripts = ["/db/migration/V1__create_schema.sql", "/db/migration/V2__sample_data.sql"])
class UserRepositoryTest {

    @Test
    fun `users are found by the name of their city`(orm: ORMTemplate, capture: SqlCapture) {
        val users = orm.repository<UserRepository>()

        val found = capture.execute { users.findByCityName("Amsterdam") }

        assertEquals(listOf("ada@example.com"), found.map { it.email })
        // The city is part of the user, so one statement reads both tables.
        assertEquals(1, capture.count(Operation.SELECT))
        assertEquals("Amsterdam", found.single().city.name)
    }

    @Test
    fun `a user is written and read back`(orm: ORMTemplate) {
        val users = orm.repository<UserRepository>()
        val cities = orm.repository<CityRepository>()
        val utrecht = cities.insertAndFetch(com.example.app.model.City(name = "Utrecht"))

        val inserted = users.insertAndFetch(
            User(
                email = "grace@example.com",
                birthDate = LocalDate.of(1906, 12, 9),
                street = "Oudegracht 3",
                postalCode = "3511 AA",
                city = utrecht
            )
        )

        assertTrue(inserted.id > 0)
        assertEquals(inserted, users.getById(inserted.id))
    }
}
