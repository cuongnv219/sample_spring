package com.kaz.passm.resource

import com.kaz.passm.domain.Account
import com.kaz.passm.domain.response.BaseResponse
import com.kaz.passm.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@RestController
@ComponentScan
@Path("/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class AccountResource : BaseResource() {

    @Autowired
    lateinit var accountService: AccountService

//    @Autowired
//    lateinit var categoryService: CategoryService

    @GET
    fun getAccountByCategory(@QueryParam("category_id") categoryId: String?): Response {
        val accounts = if (categoryId.isNullOrEmpty()) {
            accountService.getAccountByUserId(getUserId() ?: throw NullPointerException("User not found!"))
        } else {
            accountService.getAccountByCategory(getUserId() ?: throw NullPointerException("User not found!"))
        }
        return Response.ok(BaseResponse(accounts)).build()
    }

    @POST
    fun addAccount(account: Account): Response = Response.ok(
        BaseResponse(
            accountService.add(account.apply {
                userId = getUserId()
                subAccount?.forEach { it.account = this }
            })
        )
    ).build()

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: String, account: Account): Response {
        val acc = accountService.findById(id) ?: throw NullPointerException("Not found")
        acc.apply {
            name = account.name
            username = account.username
            type = account.type
            password = account.password
            subAccount = account.subAccount
        }
        return Response.ok(
            BaseResponse(accountService.update(acc))
        ).build()
    }

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): Response = Response.ok(
        BaseResponse(accountService.findById(id))
    ).build()

    @DELETE
    @Path("/{id}")
    fun deleteAccount(@PathParam("id") id: String): Response = Response.ok(
        BaseResponse(
            success = kotlin.runCatching {
                accountService.deleteById(id)
            }.isSuccess
        )
    ).build()
}
