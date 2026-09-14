package com.example.app

import com.example.app.model.City
import com.example.app.model.User
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import st.orm.template.ORMTemplate
import st.orm.test.StormTest

/**
 * Checks every entity against the schema at the JDBC level: column presence, type compatibility,
 * nullability, primary keys and foreign key consistency. The application runs the same validation
 * at startup; this test puts the failure in the build instead of in the first deployment.
 */
@StormTest(scripts = ["/db/migration/V1__create_schema.sql"])
class EntitySchemaValidationTest {

    @Test
    fun `entities match the database schema`(orm: ORMTemplate) {
        val errors = orm.validateSchema(City::class, User::class)
        assertTrue(errors.isEmpty()) { "Schema validation errors: $errors" }
    }
}
