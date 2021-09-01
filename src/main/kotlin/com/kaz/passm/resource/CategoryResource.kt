package com.kaz.passm.resource

import com.kaz.passm.domain.Category
import com.kaz.passm.domain.response.BaseResponse
import com.kaz.passm.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@RestController
@ComponentScan
@Path("/category")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CategoryResource : BaseResource() {

    @Autowired
    lateinit var categoryService: CategoryService

    @POST
    fun addCategory(category: Category): Response {
        category.userId = getUserId()
        return Response.ok(BaseResponse(categoryService.add(category))).build()
    }

    @PUT
    @Path("/{id}")
    fun updateCategory(@PathParam("id") id: String, category: Category): Response {
        val ca = categoryService.findById(id) ?: throw NullPointerException("Category not found")
        ca.mapWithRequest(category)
        return Response.ok(BaseResponse(categoryService.update(ca))).build()
    }

    @GET
    fun getCategory(): Response {
        val userId = getUserId() ?: throw NullPointerException("User not found")
        val categories = categoryService.getCategoryByUserId(userId)
        return Response.ok(BaseResponse(categories)).build()
    }

    @DELETE
    @Path("/{id}")
    fun updateCategory(@PathParam("id") id: String): Response = Response.ok(
        BaseResponse(
            kotlin.runCatching {
                categoryService.deleteById(id)
            }.isSuccess
        )
    ).build()
}
