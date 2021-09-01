package com.kaz.passm.service

import com.kaz.passm.domain.Account

interface AccountService : CrudService<Account, String> {

    fun getAccountByCategory(categoryId: String): List<Account>

    fun getAccountByCategory(categoryIds: List<String>): List<Account>

    fun getAccountByUserId(id: String): List<Account>
}
