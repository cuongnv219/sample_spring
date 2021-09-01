package com.kaz.passm.resource

import com.kaz.passm.domain.User
import org.springframework.security.core.context.SecurityContextHolder

open class BaseResource {

    fun getUserId() = (SecurityContextHolder.getContext().authentication.principal as? User)?.id

//    fun getUser() = (SecurityContextHolder.getContext().authentication.principal as? User)
}
