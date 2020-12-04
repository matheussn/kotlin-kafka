package io.github.matheussn.kotlin.kafka.truck.api

data class TruckRequest(
    val board: String,
    val model: String,
    val customerId: String
)
