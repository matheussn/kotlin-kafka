package io.github.matheussn.kotlin.kafka.simple.controller.customer

data class CustomerRequest(
    val name: String,
    val email: String,
    val truck: CustomerTruckRequest
)

data class CustomerTruckRequest(
    val board: String,
    val model: String
)