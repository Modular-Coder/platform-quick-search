package com.dake.pqs.kafka

import org.apache.kafka.streams.KafkaStreams
import org.springframework.stereotype.Service

@Service
class FindPuppiesEntryPoint(kafkaStreams: KafkaStreams) {
    init {
        kafkaStreams.start()
        Thread.sleep(60000L)
        kafkaStreams.close()
    }
}