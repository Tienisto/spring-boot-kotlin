package com.example.demo.dto

/**
 * This file contains all incoming DTOs.
 * Here, [LoginDto] is a data class containing immutable class members
 */
data class LoginDto(
    val username: String,
    val password: String,
)

data class RegisterDto(
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val password: String,
)

data class ChangeRoleDto(
    val username: String,
    val role: String,
)

data class CreateItemDto(
    val name: String,
    val count: Int,
    val note: String?,
)

data class UpdateItemDto(
    val id: Long,
    val name: String,
    val count: Int,
    val note: String?,
)
