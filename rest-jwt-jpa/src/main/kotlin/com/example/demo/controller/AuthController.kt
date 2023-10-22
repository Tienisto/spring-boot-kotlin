package com.example.demo.controller

import com.example.demo.config.toUser
import com.example.demo.dto.*
import com.example.demo.model.Role
import com.example.demo.model.User
import com.example.demo.service.HashService
import com.example.demo.service.TokenService
import com.example.demo.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

/**
 * This controller handles login and register requests.
 * Both routes are public as specified in SecurityConfig.
 */
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
) {
    @PostMapping("/v1/login")
    fun loginV1(@RequestBody payload: LoginDto): LoginResponseDto {
        val user = userService.findByName(payload.username) ?: throw ApiException(400, "Login failed")

        if (!hashService.checkBcrypt(payload.password, user.hashedPassword)) {
            throw ApiException(400, "Login failed")
        }

        return LoginResponseDto(
            token = tokenService.createToken(user),
            user = user.toDto(),
        )
    }

    @PostMapping("/v1/register")
    fun registerV1(@RequestBody payload: RegisterDto): LoginResponseDto {
        if (userService.existsByName(payload.username)) {
            throw ApiException(400, "Name already exists")
        }

        val user = User(
            username = payload.username,
            firstName = payload.firstName,
            lastName = payload.lastName,
            hashedPassword = hashService.hashBcrypt(payload.password),
            role = Role.NOT_ACTIVE
        )

        val savedUser = userService.save(user)

        return LoginResponseDto(
            token = tokenService.createToken(savedUser),
            user = savedUser.toDto()
        )
    }

    @PutMapping("/v1/changeRole")
    fun changeRoleV1(@RequestBody payload: ChangeRoleDto): Boolean {

        val user = userService.findByName(payload.username) ?: throw ApiException(400, "User Not found")

        user.role = Role.valueOf(payload.role.uppercase())

        val savedUser = userService.save(user)

        return savedUser.role.name == payload.role
    }
}
