package com.taskflow.auth

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class JwtServiceTest {
    private val service = JwtService("this-is-a-development-secret-key-with-more-than-32-bytes", 86_400_000)

    @Test
    fun `generated token contains email`() {
        val token = service.generateToken("user@example.com")
        assertEquals("user@example.com", service.extractEmail(token))
    }

    @Test
    fun `invalid token returns null`() {
        assertNull(service.extractEmail("invalid-token"))
    }
}
