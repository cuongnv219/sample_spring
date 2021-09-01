package com.kaz.passm.resource

import com.kaz.passm.domain.User
import com.kaz.passm.domain.response.BaseResponse
import com.kaz.passm.service.UserService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ComponentScan
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(tags = ["users"])
class UserResource : BaseResource() {

    @Autowired
    lateinit var userService: UserService

    @GET
    @Path("/me")
    fun getMe(): Response = Response.ok(
        BaseResponse(userService.findById(getUserId() ?: throw NullPointerException("Not found")))
    ).build()

//    @GET
//    @Path("/{id}")
//    fun getById(@PathParam("id") id: String): Response = Response.ok(BaseResponse(userService.findById(id))).build()

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String): Response {
        try {
            userService.deleteById(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Response.ok(BaseResponse()).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: String, user: User): Response {
        val u = userService.findById(id) ?: throw NullPointerException("Not found")
        u.mapWithRequest(user)
        return Response.ok(BaseResponse(userService.update(u))).build()
    }
}
