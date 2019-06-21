package com.reclameaqui.test

import com.reclameaqui.test.model.Company
import com.reclameaqui.test.model.Complain
import com.reclameaqui.test.model.Locale
import com.reclameaqui.test.service.ComplainService
import com.reclameaqui.test.util.ReverseGeoCode
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.data.mongodb.core.index.GeospatialIndex
import org.springframework.data.mongodb.core.MongoTemplate



@RunWith(SpringRunner::class)
@SpringBootTest
class ReclameaquiTestApplicationTests {

    companion object {
        init {
            System.setProperty("MONGO_HOST", "localhost")
            System.setProperty("local.mongo.port", "27017")
        }
    }

    @Autowired
    lateinit var template: MongoTemplate

    @Autowired
    lateinit var complainService: ComplainService

    @Autowired
    lateinit var reverseGeoCode: ReverseGeoCode

    @Before
    fun init() {
        template.indexOps("complain").ensureIndex(GeospatialIndex("locale.point"))
    }

    @Test
    fun createANDReadTest() {

        val point = floatArrayOf((-23.6075988).toFloat(), (-46.6957613).toFloat())
        val locale = Locale(point = point)
        val company = Company(name = "Company Test")
        val complain = Complain(title = "Reclamação Test", description = "Lorem ipsum dolor sit amet...", company = company, locale = locale)

        val uuid = complainService.createComplain(complain)

        val complainReaded = complainService.readComplain(uuid!!)

        assert(complainReaded?.company?.name.equals("Company Test"))
        assert(complainReaded?.title.equals("Reclamação Test"))
        assert(complainReaded!!.locale.city.equals("São Paulo"))
        assert(complainReaded.locale.state.equals("SP"))
        assert(complainReaded.locale.country.equals("BR"))
    }

    @Test
    fun reverseGeoCodeTest() {

        val locale = reverseGeoCode.getLocale(floatArrayOf((-23.6075988).toFloat(), (-46.6957613).toFloat()))

        assert(locale.city.equals("São Paulo"))
        assert(locale.state.equals("SP"))
        assert(locale.country.equals("BR"))
    }

    @Test
    fun createANDReadByPointCityTest() {

        val point = floatArrayOf((-23.6075988).toFloat(), (-46.6957613).toFloat())
        val locale = Locale(point = point)
        val company = Company(name = "Company Test")
        val complain = Complain(title = "Reclamação Test", description = "Lorem ipsum dolor sit amet...", company = company, locale = locale)

        complainService.createComplain(complain)

        val complainReaded = complainService.readComplainByPointCity(floatArrayOf ((-23.5614091).toFloat(), (-46.6580706).toFloat()))[0]

        assert(complainReaded.company.name.equals("Company Test"))
        assert(complainReaded.title.equals("Reclamação Test"))
        assert(complainReaded.locale.city.equals("São Paulo"))
        assert(complainReaded.locale.state.equals("SP"))
        assert(complainReaded.locale.country.equals("BR"))
    }

    @Test
    fun createANDReadByPointDistanceTest() {

        val point = floatArrayOf((-23.6075988).toFloat(), (-46.6957613).toFloat())
        val locale = Locale(point = point)
        val company = Company(name = "Company Test")
        val complain = Complain(title = "Reclamação Test", description = "Lorem ipsum dolor sit amet...", company = company, locale = locale)

        complainService.createComplain(complain)

        val complainReaded = complainService.readComplainByPointDistance(floatArrayOf ((-23.5614091).toFloat(), (-46.6580706).toFloat()), 6f)[0]

        assert(complainReaded.company.name.equals("Company Test"))
        assert(complainReaded.title.equals("Reclamação Test"))
        assert(complainReaded.locale.city.equals("São Paulo"))
        assert(complainReaded.locale.state.equals("SP"))
        assert(complainReaded.locale.country.equals("BR"))
    }

}
