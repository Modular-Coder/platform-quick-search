package com.dake.pqs

import com.dake.pqs.kafka.Kafka
import com.dake.pqs.model.SimpleListing
import com.dake.pqs.parser.BnbParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Test
import java.util.*

class PlatformQuickSearchApplicationTests {
    val fileUrl: String = "c:/Projects/resources/listings.csv"
    val parser = BnbParser()

    @Test
    fun testParse() {
        var listings: List<SimpleListing> = parser.parse(fileUrl)
        listings = parser.filterForKeyword("puppy", listings)
        listings.forEach(::print)
    }

    @Test
    fun kafkaIsWorking() {
		val topic = "SimpleListing"
		val key = UUID.randomUUID().toString()
		val value = SimpleListing("my url", "my body")
		val jsonValue = jacksonObjectMapper().writeValueAsString(value)
		val producer = Kafka().getKafkaProducer()
		val record: ProducerRecord<String, String> = ProducerRecord<String, String>(topic, key, jsonValue)

		producer.send(record)

		val consumer = Kafka().getKafkaConsumer()
		consumer.subscribe(Arrays.asList("SimpleListing"))
		var running = true

		System.out.println("Bears!")
		while (running) {
			val records: ConsumerRecords<String, String> = consumer.poll(Long.MAX_VALUE)
			System.out.println("records consumed: " + records)
			if (!records.isEmpty) {
				records.map { println(it) }
				running = false
			}

			consumer.commitSync()
		}
	}

}
