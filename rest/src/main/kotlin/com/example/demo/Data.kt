package com.example.demo

object Request {
    data class User(val name: String, val age: Int)
}

object Response {

    data class Message(val message: String)
    data class MessageWithUser(val message: String, val user: Request.User)
}