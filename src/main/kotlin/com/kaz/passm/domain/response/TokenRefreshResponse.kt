package com.kaz.passm.domain.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TokenRefreshResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: Long,
    val expiredRefreshToken: Long,
) {
}
