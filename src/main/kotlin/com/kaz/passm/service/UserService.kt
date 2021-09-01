package com.kaz.passm.service

import com.kaz.passm.domain.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : CrudService<User, String>, UserDetailsService {

    fun findByUsername(username: String): User?
}
