package com.dake.pqs.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Predicate
import org.apache.kafka.streams.kstream.Produced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetAddress
import java.util.*

@Configuration
class KafkaConfig {

    @Bean
    fun getKafkaProducer(): KafkaProducer<String, String> {
        val config = Properties()
        config["client.id"] = InetAddress.getLocalHost().hostName
        config["bootstrap.servers"] = "localhost:9092"
        config["acks"] = "all"
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"

        return KafkaProducer<String, String>(config)
    }

    @Bean
    fun getKafkaConsumer(): KafkaConsumer<String, String> {
        val config = Properties()
        config["client.id"] = InetAddress.getLocalHost().hostName
        config["group.id"] = "group1"
        config["bootstrap.servers"] = "localhost:9092"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"

        return KafkaConsumer<String, String>(config)
    }

    @Bean
    fun petTopology(): KafkaStreams {
        val props = Properties()
        props["client.id"] = InetAddress.getLocalHost().hostName
        props["bootstrap.servers"] = "localhost:9092"
        props["application.id"] = "BnbQuickSearch"
        val stringSerde: Serde<String> = Serdes.String()
        val streamsBuilder = StreamsBuilder()

        //source node
        val listingStream: KStream<String, String> = streamsBuilder.stream("SimpleListing", Consumed.with(stringSerde, stringSerde))

        //processors
        val upperCaseStream: KStream<String, String> = listingStream.mapValues { key, value -> value.toUpperCase() }
        val petStreams: Array<KStream<String, String>> = upperCaseStream.branch(
                Predicate { _, value -> containsPuppy(value) },
                Predicate { _, value -> containsKitty(value) }
        )

        val puppyStream: KStream<String, String> = petStreams[0]
        val kittyStream: KStream<String, String> = petStreams[1]

        kittyStream.peek { key, value -> println("Kitty:" + value) }

        //sink nodes
        puppyStream.to("Puppies", Produced.with(stringSerde, stringSerde))
        kittyStream.to("Cats", Produced.with(stringSerde, stringSerde))

        return KafkaStreams(streamsBuilder.build(), props)
    }

    fun containsPuppy(body: String): Boolean = body.contains("SHIH TZU") || body.contains("BICHON") || body.contains("GOLDEN RETRIEVER") || body.contains("DOODLE")

    fun containsKitty(body: String): Boolean = body.contains("CATS") || body.contains("KITTEN") || body.contains("KITTY")


}