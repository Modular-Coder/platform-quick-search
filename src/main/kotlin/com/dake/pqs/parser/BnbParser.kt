package com.dake.pqs.parser

import com.dake.pqs.model.SimpleListing
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Component
import java.io.FileReader
import java.io.Reader

@Component
class BnbParser() {
    fun createReader(fileUrl: String) = FileReader(fileUrl)

    fun parseFile(reader: Reader): Iterable<CSVRecord> = CSVFormat.DEFAULT
            .withDelimiter(',')
            .withQuote('"')
            .withRecordSeparator("\r\n")
            .withIgnoreEmptyLines(true)
            .withAllowDuplicateHeaderNames(true)
            .parse(reader)

    fun mapToSimpleListing(record: CSVRecord): SimpleListing = SimpleListing(record.get(1), record.get(7))

    fun mapToSimpleListings(records: Iterable<CSVRecord>): List<SimpleListing> = records.map { record -> mapToSimpleListing(record) }

    fun parse(fileUrl: String): List<SimpleListing> = mapToSimpleListings(parseFile(createReader(fileUrl)))

    fun filterForKeyword(keyword: String, listings: List<SimpleListing>): List<SimpleListing> = listings.filter { simpleListing -> simpleListing.body.contains(keyword) }

}