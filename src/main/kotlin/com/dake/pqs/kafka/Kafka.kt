package com.dake.pqs.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Configuration
import java.net.InetAddress
import java.util.*


@Configuration
class Kafka {
    fun getKafkaProducer(): KafkaProducer<String, String> {
        val config = Properties()
        config["client.id"] = InetAddress.getLocalHost().hostName
        config["bootstrap.servers"] = "localhost:9092"
        config["acks"] = "all"
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"

        return KafkaProducer<String, String>(config)
    }

    fun getKafkaConsumer(): KafkaConsumer<String, String> {
        val config = Properties()
        config["client.id"] = InetAddress.getLocalHost().hostName
        config["group.id"] = "group1"
        config["bootstrap.servers"] = "localhost:9092"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"

        return KafkaConsumer<String, String>(config)
    }
}