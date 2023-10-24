package com.example.demo.dto

import com.example.demo.model.Role
import org.springframework.web.server.ResponseStatusException

/**
 * This file contains all outgoing DTOs.
 * [ApiException] is used to easily throw exceptions.
 */
class ApiException(code: Int, message: String) : ResponseStatusException(code, message, null)

data class LoginResponseDto(
    val token: String,
    val user: UserDto,
)


data class UserDto(
    val id: Long,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val role: Role,
)


data class ItemDto(
    val id: Long,
    val name: String,
    val count: Int,
    val note: String?,
)
