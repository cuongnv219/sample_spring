package com.kaz.passm.auth

import com.kaz.passm.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//@Component
class AuthTokenFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var userDetailsService: UserService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filter: FilterChain) {
        try {
            parseJwt(request).let { jwt ->
                if (jwtUtils.validateJwtToken(jwt)) {
                    val username = jwtUtils.getUserNameFromJwtToken(jwt)
                    val userDetails = userDetailsService.findByUsername(username)
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, ArrayList()
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: {}", e)
        }
        filter.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7)
        } else null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)
    }
}
