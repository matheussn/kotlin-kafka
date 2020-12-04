package io.github.matheussn.kotlin.kafka.truck.api

import io.github.matheussn.kotlin.kafka.truck.domain.Truck
import io.github.matheussn.kotlin.kafka.truck.domain.TruckRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RequestMapping("/v1/truck")
@RestController
class TruckController(
    private val truckRepository: TruckRepository
) {
    @GetMapping
    fun getTrucks(): List<TruckResponse> =
        truckRepository.findAll().map { it.toResponse() }

    @PostMapping
    fun createTrucks(
        @RequestBody trucks: List<TruckRequest>
    ): List<TruckResponse> =
        truckRepository.saveAll(trucks.map { it.toEntity() }).map { it.toResponse() }

    fun TruckRequest.toEntity() =
        Truck(UUID.randomUUID().toString(), board, model, customerId)

    fun Truck.toResponse() =
        TruckResponse(id, board, model, customerId)
}