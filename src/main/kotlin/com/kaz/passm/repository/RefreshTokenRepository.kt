package com.kaz.passm.repository

import com.kaz.passm.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {

    fun findByToken(token: String?): RefreshToken?
}
