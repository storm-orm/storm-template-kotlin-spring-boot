package com.example.app.repository

import com.example.app.model.User
import com.example.app.model.User_
import st.orm.repository.EntityRepository
import st.orm.template.eq

/**
 * findById, insert, update, upsert and the rest come with EntityRepository; this interface adds
 * the queries the application needs. Storm registers the implementation itself, so there is no
 * class to write and nothing to register.
 */
interface UserRepository : EntityRepository<User, Int> {

    /**
     * Users living in a city, by the city's name. The metamodel path navigates the foreign key, so
     * Storm joins the city table for the predicate without the query naming the join.
     */
    fun findByCityName(cityName: String): List<User> =
        select()
            .where(User_.city.name eq cityName)
            .orderBy(User_.email)
            .resultList
}
