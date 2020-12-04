package io.github.matheussn.kotlin.kafka.simple.config

import io.github.matheussn.kotlin.kafka.simple.controller.dto.TruckDto
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaConfig {
    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Value("\${group.truck.consumer}")
    private lateinit var groupId: String

    @Value(value = "\${topics.create.truck}")
    lateinit var kafkaTopic: String

    @Bean
    fun kafkaAdmin(): KafkaAdmin = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers))

    @Bean
    fun topic() = NewTopic(kafkaTopic, 1, 1)

    @Bean
    fun producerConfig(): ProducerFactory<String, Any> =
        DefaultKafkaProducerFactory(
            mapOf<String, Any>(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
            )
        )

    @Bean
    fun kafkaTemplate() = KafkaTemplate(producerConfig())

    @Bean
    fun consumerFactory(): ConsumerFactory<String, TruckDto> {
        val deserializer = JsonDeserializer(TruckDto::class.java, false)

        return DefaultKafkaConsumerFactory(
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to groupId,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to deserializer
            ),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun truckKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TruckDto> =
        ConcurrentKafkaListenerContainerFactory<String, TruckDto>().apply { consumerFactory = consumerFactory() }
}