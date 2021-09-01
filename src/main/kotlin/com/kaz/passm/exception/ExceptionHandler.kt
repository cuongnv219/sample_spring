package com.kaz.passm.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import java.sql.SQLException
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
@Configuration
class ExceptionHandler : ExceptionMapper<Throwable> {

    @Context
    lateinit var uriInfo: UriInfo

    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    override fun toResponse(e: Throwable?): Response {
        logger.error(e?.message, e)

        val status: Response.StatusType = when (e) {
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

        val errorPayload = ErrorPayload(
            status.statusCode,
            status.reasonPhrase,
            listOf(e?.message ?: ""),
            uriInfo.requestUri.toString()
        )

        return Response.status(status.statusCode)
            .entity(errorPayload)
            .type(MediaType.APPLICATION_JSON)
            .build()
    }
}
