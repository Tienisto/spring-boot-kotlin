package com.example.demo

import com.fasterxml.jackson.annotation.JsonInclude

object Request {
    data class User(val name: String, val age: Int)
}

object Response {

    // all responses will be wrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class Wrapper(val success: Boolean = true,
                       val token: String? = null,
                       val data: Any?)
}