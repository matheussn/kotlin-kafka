package io.github.matheussn.kotlin.kafka.customer.api

import io.github.matheussn.kotlin.kafka.customer.domain.Customer
import io.github.matheussn.kotlin.kafka.customer.domain.CustomerRepository
import io.github.matheussn.kotlin.kafka.customer.domain.TruckDto
import io.github.matheussn.kotlin.kafka.customer.producer.CustomerProducer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/customer")
class CustomerController(
    private val customerRepository: CustomerRepository,
    private val customerProducer: CustomerProducer
) {
    @GetMapping
    fun getCustomers(): List<CustomerResponse> =
        customerRepository.findAll().map { it.toResponse() }

    @PostMapping
    fun createCustomer(@RequestBody customer: CustomerRequest): CustomerResponse =
        customerRepository.save(customer.toEntity())
            .also { customerProducer.createTruck(customer.toTruckDto(it.id)) }
            .toResponse()

    @PostMapping("/list")
    fun createCustomerWithListOfTruck(@RequestBody customer: CustomerTruckListRequest): CustomerResponse =
        customerRepository.save(customer.toEntity())
            .also { customer.toTruckDto(it.id).map { truck -> customerProducer.createTruck(truck) } }
            .toResponse()

    private fun Customer.toResponse() =
        CustomerResponse(id, name, email)

    private fun CustomerRequest.toEntity() =
        Customer(id = UUID.randomUUID().toString(), name = name, email = email)

    fun CustomerRequest.toTruckDto(id: String) =
        TruckDto(board = this.truck.board, model = this.truck.model, customerId = id)

    private fun CustomerTruckListRequest.toEntity() =
        Customer(id = UUID.randomUUID().toString(), name = name, email = email)

    fun CustomerTruckListRequest.toTruckDto(id: String) =
        this.truck.map { TruckDto(board = it.board, model = it.model, customerId = id) }
}
