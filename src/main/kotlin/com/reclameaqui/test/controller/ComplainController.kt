package com.reclameaqui.test.controller

import com.reclameaqui.test.model.Complain
import com.reclameaqui.test.service.ComplainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/reclameaqui")
class ComplainController {

    @Autowired
    lateinit var complainService: ComplainService

    @PostMapping("/complains")
    fun createComplain(@RequestBody complain: Complain) : ResponseEntity<Any> {

        var uuid: String? = complainService.createComplain(complain) ?: return ResponseEntity.status(409).build()

        return ResponseEntity.created(URI("/complains/$uuid")).build()
    }

    @GetMapping("/complains/{uuid}")
    fun readComplain(@PathVariable uuid: String) : Complain? {

        return complainService.readComplain(uuid)
    }

    @GetMapping("/complains/point/city/{latitude}/{longitude}")
    fun readComplainByPointCity(@PathVariable latitude: Float, @PathVariable longitude: Float) : List<Complain> {

        return complainService.readComplainByPointCity(floatArrayOf(latitude, longitude))
    }

    @GetMapping("/complains/point/{latitude}/{longitude}/{distance}")
    fun readComplainByPointDistance(@PathVariable latitude: Float, @PathVariable longitude: Float, @PathVariable distance: Float) : List<Complain> {

        return complainService.readComplainByPointDistance(floatArrayOf(latitude, longitude), distance)
    }

    @GetMapping("/complains")
    fun readAllComplain() : List<Complain> {

        return complainService.readAllComplain()
    }

    @PutMapping("/complains/{uuid}")
    fun update(@PathVariable uuid: String, @RequestBody complain: Complain) : Complain? {

        return complainService.update(uuid, complain)
    }

    @DeleteMapping("/complains/{uuid}")
    fun deleteComplain(@PathVariable uuid: String) {

        complainService.deleteComplain(uuid)
    }

}