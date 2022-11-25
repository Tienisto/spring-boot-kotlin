package com.example.demo.config

import com.example.demo.model.User
import com.example.demo.service.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component

/**
 * This class takes the parsed JWT token and validates it.
 * The granted authority will be "USER" but it has no effect in this application as all users are "USER"
 */
@Component
class JwtAuthenticationManager(
    private val tokenService: TokenService,
) : AuthenticationManager {
    override fun authenticate(authentication: Authentication): Authentication {
        val jwt = authentication as BearerTokenAuthenticationToken
        val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
        return UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority("USER")))
    }
}

/**
 * Shorthand for controllers accessing the authenticated user.
 */
fun Authentication.toUser(): User {
    return principal as User
}
