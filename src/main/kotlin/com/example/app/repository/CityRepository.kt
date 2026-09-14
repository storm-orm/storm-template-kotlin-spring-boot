package com.example.app.repository

import com.example.app.model.City
import com.example.app.model.City_
import st.orm.repository.EntityRepository
import st.orm.template.eq

interface CityRepository : EntityRepository<City, Int> {

    /** The city with this name, or null when there is none. */
    fun findByName(name: String): City? =
        select()
            .where(City_.name eq name)
            .optionalResult
}
