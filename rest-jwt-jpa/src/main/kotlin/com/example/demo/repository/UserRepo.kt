package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepo : CrudRepository<User, Long> {
    fun findByUsername(name: String): User?
    fun existsByUsername(name: String): Boolean
}
