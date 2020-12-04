package io.github.matheussn.kotlin.kafka.simple.domain.truck

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Truck(
    @Id
    val id: String,
    val board: String,
    val model: String,
    val customerId: String
)
