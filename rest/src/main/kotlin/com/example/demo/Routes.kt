package com.example.demo

import org.springframework.web.bind.annotation.*

@RestController
class Routes {

    @GetMapping("/")
    fun getRoot(): Response.Message {
        return Response.Message("Hello World")
    }

    // e.g. /hello
    @GetMapping("/{message}")
    fun getWithPathParameter(@PathVariable message: String): Response.Message {
        return Response.Message("You typed in: $message")
    }

    // e.g. /query?message=hello
    @GetMapping("/query")
    fun getWithQueryParameter(@RequestParam message: String): Response.Message {
        return Response.Message("You typed in the query: $message")
    }

    @PostMapping("/update")
    fun updateSomething(@RequestBody request: Request.User): Response.MessageWithUser {

        // some database operations

        return Response.MessageWithUser("User saved.", request)
    }

    @DeleteMapping("/{id}")
    fun deleteSomething(@PathVariable id: Int): Response.Message {

        // delete the user by the id
        println("Deleting user with id $id.")

        return Response.Message("User deleted. (id = $id)")
    }
}