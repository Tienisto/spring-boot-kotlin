package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepo,
) {
    fun findById(id: Long): User? {
        return userRepo.findByIdOrNull(id)
    }

    fun findByName(name: String): User? {
        return userRepo.findByUsername(name)
    }

    fun existsByName(name: String): Boolean {
        return userRepo.existsByUsername(name)
    }

    fun save(user: User): User {
        return userRepo.save(user)
    }
}
