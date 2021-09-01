package com.kaz.passm.exception

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import java.sql.SQLException
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

@ControllerAdvice
@Configuration
class ExceptionSpring {

    companion object {
        private val logger = LoggerFactory.getLogger(ExceptionSpring::class.java)
    }

    fun springErrorHandler(request: HttpServletRequest, e: Exception): ResponseEntity<Any> {
        logger.error(e.message, e)

        val status = when (e) {
            is WebApplicationException -> {
                e.response.statusInfo
            }
            is SQLException -> {
                Response.Status.BAD_REQUEST
            }
            else -> {
                Response.Status.INTERNAL_SERVER_ERROR
            }
        }
        val payload = ErrorPayload(
            status.statusCode,
            status.reasonPhrase,
            System.currentTimeMillis(),
            listOf(e.message ?: ""),
            request.requestURI
        )

        return ResponseEntity.status(status.statusCode).body(payload)
    }
}
