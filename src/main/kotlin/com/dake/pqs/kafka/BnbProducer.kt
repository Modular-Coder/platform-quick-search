package com.dake.pqs.kafka

import com.dake.pqs.model.SimpleListing
import com.dake.pqs.parser.BnbParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

//@Component
class BnbProducer(val kafkaProducer: KafkaProducer<String, String>, val parser: BnbParser) {

    val topicName: String = "SimpleListing"

    fun produceRecordsFromFile(fileUrl: String) {
        val records: List<SimpleListing> = parser.parse(fileUrl)
        records.map { simpleListing -> jacksonObjectMapper().writeValueAsString(simpleListing) }
                .map { json -> ProducerRecord<String, String>(topicName, UUID.randomUUID().toString(), json) }
                .map { producerRecord -> kafkaProducer.send(producerRecord) }
    }
}