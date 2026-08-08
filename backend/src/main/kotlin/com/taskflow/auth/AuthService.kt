package com.taskflow.auth

import com.taskflow.user.User
import com.taskflow.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val users: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    fun register(request: RegisterRequest): AuthResponse {
        require(!users.existsByEmail(request.email)) { "Email is already registered" }
        val user = users.save(User(email = request.email.lowercase(), passwordHash = passwordEncoder.encode(request.password)))
        return AuthResponse(jwtService.generateToken(user.email))
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = users.findByEmail(request.email.lowercase()).orElseThrow { IllegalArgumentException("Invalid credentials") }
        require(passwordEncoder.matches(request.password, user.passwordHash)) { "Invalid credentials" }
        return AuthResponse(jwtService.generateToken(user.email))
    }
}
