package com.kaz.passm.domain.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.io.Serializable

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TokenRefreshRequest(
    val refreshToken: String?
) : Serializable
