package com.taskflow.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.Date

@Service
class JwtService(
    @Value("${'$'}{app.jwt.secret}") secret: String,
    @Value("${'$'}{app.jwt.expiration-ms:86400000}") private val expirationMs: Long
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(email: String): String = Jwts.builder()
        .setSubject(email)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + expirationMs))
        .signWith(key)
        .compact()

    fun extractEmail(token: String): String? = runCatching {
        Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).body.subject
    }.getOrNull()
}
