package io.github.matheussn.kotlin.kafka.truck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TruckApplication

fun main(args: Array<String>) {
    runApplication<TruckApplication>(*args)
}