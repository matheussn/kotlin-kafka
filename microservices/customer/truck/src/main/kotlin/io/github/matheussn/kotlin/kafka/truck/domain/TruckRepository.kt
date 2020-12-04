package io.github.matheussn.kotlin.kafka.truck.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TruckRepository : JpaRepository<Truck, String>