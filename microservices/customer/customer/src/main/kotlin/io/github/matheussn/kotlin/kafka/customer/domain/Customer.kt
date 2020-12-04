package io.github.matheussn.kotlin.kafka.customer.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Customer(
    @Id
    val id: String,
    val name: String,
    val email: String
)
