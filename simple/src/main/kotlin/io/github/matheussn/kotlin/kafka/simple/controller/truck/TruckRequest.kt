package io.github.matheussn.kotlin.kafka.simple.controller.truck

data class TruckRequest(
    val board: String,
    val model: String,
    val customerId: String
)
