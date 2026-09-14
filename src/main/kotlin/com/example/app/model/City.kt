package com.example.app.model

import st.orm.DbTable
import st.orm.Entity
import st.orm.PK

/**
 * A city. Entities are immutable data classes: Storm reads them straight out of the result set and
 * writes them back without proxies, a persistence context, or a session to keep open.
 */
@DbTable("city")
data class City(
    @PK val id: Int = 0,
    val name: String
) : Entity<Int>
