package com.example.demo

import org.springframework.web.bind.annotation.*

@RestController
class Routes {

    @GetMapping("/")
    fun getDefaultWrapper() {}

    // e.g. /hello
    @GetMapping("/number")
    fun getWrapperWithMessage(): Int {
        return 42
    }

    @GetMapping("/explicit/{message}")
    fun getExplicitWrapper(@PathVariable message: String): Response.Wrapper {
        return Response.Wrapper(true, "abc", "the message: $message")
    }

    @PostMapping("/update")
    fun updateSomething(@RequestBody request: Request.User): Request.User {
        // some database operations
        return request
    }
}