package com.kaz.passm.service.impl

import com.kaz.passm.domain.User
import com.kaz.passm.repository.UserRepository
import com.kaz.passm.service.UserDetailsImpl
import com.kaz.passm.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl : CrudServiceImpl<User, String>(), UserService {

    @Autowired
    lateinit var userRepository: UserRepository

//    @Autowired
//    lateinit var passwordEncoder: PasswordEncoder

    override fun mainRepository() = userRepository

    override fun findByUsername(username: String) = userRepository.findByUsername(username)

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User Not Found with username: $username")
        return UserDetailsImpl.build(user)
    }
}
