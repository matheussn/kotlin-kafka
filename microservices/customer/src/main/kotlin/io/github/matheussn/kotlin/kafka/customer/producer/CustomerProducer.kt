package io.github.matheussn.kotlin.kafka.customer.producer

import io.github.matheussn.kotlin.kafka.customer.domain.TruckDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class CustomerProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    @Value("\${topics.create.truck}")
    private lateinit var createTruckTopic: String

    fun createTruck(truck: TruckDto) {
        kafkaTemplate.send(createTruckTopic, truck)
            .addCallback(object : ListenableFutureCallback<SendResult<String, Any>> {
                override fun onSuccess(result: SendResult<String, Any>?) {
                    println(
                        "Sent message=[" + truck.model +
                                "] with offset=[" + result?.recordMetadata?.offset() + "]"
                    )
                }

                override fun onFailure(ex: Throwable) {
                    println(
                        "Unable to send message=["
                                + truck.model + "] due to : " + ex.message
                    )
                }
            })
    }
}