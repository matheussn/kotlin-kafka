package io.github.matheussn.kotlin.kafka.simple.domain.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, String>