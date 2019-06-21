package com.reclameaqui.test.service

import com.reclameaqui.test.model.Complain
import com.reclameaqui.test.repository.ComplainRepository
import com.reclameaqui.test.util.ReverseGeoCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ComplainService {

    @Autowired
    lateinit var complainRepository: ComplainRepository

    @Autowired
    lateinit var reverseGeoCode: ReverseGeoCode

    fun createComplain(complain: Complain) : String? {

        if(complain.uuid != null) {
            return null
        }

        complain.locale = reverseGeoCode.getLocale(complain.locale.point)

        return complainRepository.insert(complain).uuid
    }

    fun readComplain(uuid: String) : Complain? {

        return complainRepository.findById(uuid).orElse(null)
    }

    fun readComplainByPointCity(point: FloatArray) : List<Complain> {

        val locale = reverseGeoCode.getLocale(point)

        return complainRepository.findByLocaleCityAndLocaleStateAndLocaleCountry(locale.city, locale.state, locale.country)
    }

    fun readComplainByPointDistance(point: FloatArray, distance: Float) : List<Complain> {

        return complainRepository.findByLocalePointNear(org.springframework.data.geo.Point(point[0].toDouble(), point[1].toDouble()), org.springframework.data.geo.Distance(distance.toDouble(),  org.springframework.data.geo.Metrics.KILOMETERS))
    }

    fun readAllComplain(): List<Complain> {

        return complainRepository.findAll()
    }

    fun update(uuid: String, complain: Complain) : Complain? {

        if(complainRepository.existsById(uuid)) {
            return null
        }

        complain.uuid = uuid
        complain.locale = reverseGeoCode.getLocale(complain.locale.point)

        return complainRepository.save(complain)
    }

    fun deleteComplain(uuid: String) {

        complainRepository.deleteById(uuid)
    }

}