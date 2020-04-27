package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nested")
class NestedRoutes {

    @GetMapping("/first")
    fun first(): Response.Message {
        return Response.Message("First message")
    }

    @GetMapping("/second")
    fun second(): Response.Message {
        return Response.Message("Second message")
    }
}