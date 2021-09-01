package com.kaz.passm.service

import com.kaz.passm.domain.RefreshToken

interface RefreshTokenService : CrudService<RefreshToken, String> {

    fun findByToken(token: String?): RefreshToken?

    fun createRefreshToken(userId: String): RefreshToken

    fun verifyExpiration(token: RefreshToken): RefreshToken

    fun deleteByUserId(userId: String)
}
