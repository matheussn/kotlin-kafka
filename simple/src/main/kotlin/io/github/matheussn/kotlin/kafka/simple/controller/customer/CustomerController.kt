package io.github.matheussn.kotlin.kafka.simple.controller.customer

import io.github.matheussn.kotlin.kafka.simple.controller.customer.producer.KafkaSend
import io.github.matheussn.kotlin.kafka.simple.controller.dto.TruckDto
import io.github.matheussn.kotlin.kafka.simple.domain.customer.Customer
import io.github.matheussn.kotlin.kafka.simple.domain.customer.CustomerRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RequestMapping("/v1/customer")
@RestController
class CustomerController(
    private val customerRepository: CustomerRepository,
    private val kafkaSend: KafkaSend
) {
    @GetMapping
    fun getCustomers(): List<CustomerResponse> =
        customerRepository.findAll().map { it.toResponse() }

    @PostMapping
    fun createCustomer(@RequestBody customer: CustomerRequest): CustomerResponse =
        customerRepository.save(customer.toEntity())
            .also { kafkaSend.createTruck(customer.toTruckDto(it.id)) }
            .toResponse()

    fun Customer.toResponse() =
        CustomerResponse(id, name, email)

    fun CustomerRequest.toEntity() =
        Customer(id = UUID.randomUUID().toString(), name = name, email = email)

    fun CustomerRequest.toTruckDto(id: String) =
        TruckDto(board = this.truck.board, model = this.truck.model, customerId = id)
}