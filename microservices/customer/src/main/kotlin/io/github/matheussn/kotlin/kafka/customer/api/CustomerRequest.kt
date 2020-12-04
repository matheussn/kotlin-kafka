package io.github.matheussn.kotlin.kafka.customer.api

data class CustomerRequest(
    val name: String,
    val email: String,
    val truck: CustomerTruckRequest
)

data class CustomerTruckListRequest(
    val name: String,
    val email: String,
    val truck: List<CustomerTruckRequest>
)

data class CustomerTruckRequest(
    val board: String,
    val model: String
)