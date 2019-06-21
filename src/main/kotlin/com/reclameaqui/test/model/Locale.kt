package com.reclameaqui.test.model

import java.io.Serializable

data class Locale (

        val point: FloatArray = floatArrayOf(0f, 0f),

        var city: String? = null,

        var state: String? = null,

        var country: String? = null

) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Locale

        if (!point.contentEquals(other.point)) return false

        return true
    }

    override fun hashCode(): Int {
        return point.contentHashCode()
    }
}