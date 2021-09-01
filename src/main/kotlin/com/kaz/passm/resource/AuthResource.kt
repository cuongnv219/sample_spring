package com.kaz.passm.resource

import com.kaz.passm.auth.JwtUtils
import com.kaz.passm.domain.User
import com.kaz.passm.domain.request.TokenRefreshRequest
import com.kaz.passm.domain.response.BaseResponse
import com.kaz.passm.domain.response.JwtResponse
import com.kaz.passm.domain.response.TokenRefreshResponse
import com.kaz.passm.exception.TokenRefreshException
import com.kaz.passm.service.RefreshTokenService
import com.kaz.passm.service.UserDetailsImpl
import com.kaz.passm.service.UserService
import com.kaz.passm.utils.Constants
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@RestController
@ComponentScan
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class AuthResource {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @POST
    @Path("/register")
    fun signUp(user: User?): Response {
        if (user == null) {
            throw BadRequestException()
        }
        val raw = userService.findByUsername(user.username!!)
        if (raw != null) {
            throw Exception(Constants.USERNAME_IS_TAKEN)
        }
        userService.add(user.apply {
            this.password = encoder.encode(this.password)
        })
        return Response.ok(
            BaseResponse(
                data = Constants.REGISTER_SUCCESS
            )
        ).build()
    }

    @POST
    @Path("/login")
    @PostMapping(value = ["api/auth/login"])
    @ApiOperation(
        value = "Login",
        response = JwtResponse::class,
    )
    fun login(user: User?): Response {
        if (user == null) {
            throw BadRequestException()
        }
        val username = user.username ?: ""
        val password = user.password
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as UserDetailsImpl
        val refreshToken = refreshTokenService.createRefreshToken(userDetails.id)

        return Response.ok(
            JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.email,
                refreshToken.token,
                System.currentTimeMillis() + jwtUtils.jwtExpirationMs
            )
        ).build()
    }

    @POST
    @Path("/refreshtoken")
    fun refreshToken(refreshRequest: TokenRefreshRequest): Response {
        val requestRefreshToken = refreshRequest.refreshToken

        var token = refreshTokenService.findByToken(requestRefreshToken)
            ?: throw TokenRefreshException(message = "Refresh token is not in database!")
        token = refreshTokenService.verifyExpiration(token)
        val user = token.user
        val newToken = jwtUtils.generateTokenFromUsername(user?.username)
        return Response.ok(
            TokenRefreshResponse(
                accessToken = newToken ?: "",
                refreshToken = requestRefreshToken ?: "",
                expiredAt = System.currentTimeMillis() + jwtUtils.jwtExpirationMs,
                expiredRefreshToken = System.currentTimeMillis() + jwtUtils.refreshTokenDurationMs
            )
        ).build()
    }
}
