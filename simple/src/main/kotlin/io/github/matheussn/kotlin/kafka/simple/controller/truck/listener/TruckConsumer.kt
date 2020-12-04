package io.github.matheussn.kotlin.kafka.simple.controller.truck.listener

import io.github.matheussn.kotlin.kafka.simple.controller.dto.TruckDto
import io.github.matheussn.kotlin.kafka.simple.domain.truck.Truck
import io.github.matheussn.kotlin.kafka.simple.domain.truck.TruckRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TruckConsumer(
    private val truckRepository: TruckRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["\${topics.create.truck}"],
        groupId = "\${group.truck.consumer}",
        containerFactory = "truckKafkaListenerContainerFactory"
    )
    fun listenerTruck(truckDto: TruckDto) {
        logger.info("[TRUCK LISTENER] INIT CREATE TRUCK")
        truckRepository.save(truckDto.toEntity())
        logger.info("[TRUCK LISTENER] FINISH CREATE TRUCK")
    }

    fun TruckDto.toEntity() =
        Truck(id = UUID.randomUUID().toString(), board = board, model = model, customerId = customerId)
}