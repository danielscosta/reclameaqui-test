package com.reclameaqui.test.util

import com.google.maps.GeoApiContext
import com.reclameaqui.test.model.Locale
import com.google.maps.GeocodingApi
import com.google.maps.model.AddressComponentType
import com.google.maps.model.LatLng
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ReverseGeoCode {

    @Value("\${googleApiKey}")
    lateinit var googleApiKey: String

    fun getLocale(point: FloatArray): Locale {

        val latlng = LatLng(point[0].toDouble(), point[1].toDouble())
        val results = GeocodingApi.newRequest(GeoApiContext.Builder().apiKey(googleApiKey).build()).latlng(latlng).await()

        val locale = Locale(point = point)

        for(ac in results[0].addressComponents) {
            for (acType in ac.types) {

                when (acType) {
                    AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1 -> locale.state = ac.shortName
                    AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2 -> locale.city = ac.shortName
                    AddressComponentType.COUNTRY -> locale.country = ac.shortName
                    else -> {}
                }
            }
        }

        return locale
    }
}