package io.github.matheussn.kotlin.kafka.simple.domain.truck

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TruckRepository : JpaRepository<Truck, String>