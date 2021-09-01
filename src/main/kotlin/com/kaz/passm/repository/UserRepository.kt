package com.kaz.passm.repository

import com.kaz.passm.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

//    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String?): User?
}
