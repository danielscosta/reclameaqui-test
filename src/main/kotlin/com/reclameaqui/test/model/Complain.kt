package com.reclameaqui.test.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document
data class Complain (

        @JsonIgnore
        @Id
        var uuid: String? = null,

        val title: String,

        val description: String,

        var locale: Locale,

        val company: Company

) : Serializable