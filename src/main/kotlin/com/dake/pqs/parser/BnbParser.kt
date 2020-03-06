package com.dake.pqs.parser

import com.dake.pqs.model.BnbListing
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Component
import java.io.FileReader
import java.io.Reader

@Component
class BnbParser {
    fun createReader(fileUrl: String) = FileReader(fileUrl)

    fun parseFile(reader: Reader): Iterable<CSVRecord> = CSVFormat.DEFAULT
            .withDelimiter(',')
            .withQuote('"')
            .withRecordSeparator("\r\n")
            .withIgnoreEmptyLines(true)
            .withAllowDuplicateHeaderNames(true)
            .parse(reader)

    fun mapToBnbListing(record: CSVRecord): BnbListing = BnbListing()

    fun parseFileAndPrint(fileName: String) = parseFile(createReader(fileName)).map { record: CSVRecord -> println(record.get(1)) }

    fun parse() = parseFileAndPrint("c:/Projects/resources/listings.csv")
}