package com.kaz.passm.service.impl

import com.kaz.passm.domain.RefreshToken
import com.kaz.passm.exception.TokenRefreshException
import com.kaz.passm.repository.RefreshTokenRepository
import com.kaz.passm.service.RefreshTokenService
import com.kaz.passm.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class RefreshTokenServiceImpl : CrudServiceImpl<RefreshToken, String>(), RefreshTokenService {

    @Value("\${kaz.app.jwtRefreshExpirationMs}")
    private val refreshTokenDurationMs: Long = 0

    @Autowired
    lateinit var refreshTokenRepository: RefreshTokenRepository

    @Autowired
    lateinit var userRepository: UserService

    override fun findByToken(token: String?) = refreshTokenRepository.findByToken(token)

    override fun createRefreshToken(userId: String): RefreshToken {
        var refreshToken = RefreshToken(
            user = userRepository.findById(userId),
            expiryDate = Instant.now().plusMillis(refreshTokenDurationMs),
            token = UUID.randomUUID().toString(),
        )
        refreshToken = refreshTokenRepository.save(refreshToken)
        return refreshToken
    }

    override fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.expiryDate < Instant.now()) {
            refreshTokenRepository.delete(token)
            throw TokenRefreshException(token.token, "Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    @Transactional
    override fun deleteByUserId(userId: String) {
        val user = userRepository.findById(userId) ?: return
        refreshTokenRepository.deleteById(user.id)
    }

    override fun mainRepository() = refreshTokenRepository
}
