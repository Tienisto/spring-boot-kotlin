package com.example.demo.config

import com.example.demo.model.User
import org.springframework.security.core.Authentication

/**
 * Shorthand for controllers accessing the authenticated user.
 */
fun Authentication.toUser(): User {
    return principal as User
}
