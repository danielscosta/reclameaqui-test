package com.reclameaqui.test.repository

import com.reclameaqui.test.model.Complain
import org.springframework.data.geo.Point
import org.springframework.data.geo.Distance
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ComplainRepository : MongoRepository<Complain, String> {

    fun findByLocaleCityAndLocaleStateAndLocaleCountry(city: String?, state: String?, country: String?): List<Complain>

    fun findByLocalePointNear(point: Point, distance: Distance): List<Complain>
}