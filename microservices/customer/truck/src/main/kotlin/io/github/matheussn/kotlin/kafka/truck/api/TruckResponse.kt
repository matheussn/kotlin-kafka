package io.github.matheussn.kotlin.kafka.truck.api

data class TruckResponse(
    val id: String,
    val board: String,
    val model: String,
    val customerId: String
)
