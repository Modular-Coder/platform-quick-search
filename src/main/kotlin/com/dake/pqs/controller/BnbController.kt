package com.dake.pqs.controller

import com.dake.pqs.parser.BnbParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BnbController {
    @GetMapping("/hello")
    fun greeting(): String {
        BnbParser().parse()
        return "hi"
    }
}

