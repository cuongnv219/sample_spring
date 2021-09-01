package com.kaz.passm.domain.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class JwtResponse(
    var accessToken: String,
    private val id: String,
    var username: String,
    var email: String,
    var refreshToken: String,
    var expiredAt: Long
)
