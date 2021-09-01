package com.kaz.passm.service.impl

import com.kaz.passm.domain.Account
import com.kaz.passm.repository.AccountRepository
import com.kaz.passm.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl : CrudServiceImpl<Account, String>(), AccountService {

    @Autowired
    lateinit var accountRepository: AccountRepository

    override fun mainRepository() = accountRepository

    override fun getAccountByCategory(categoryId: String) = accountRepository.getAccountByCategory(categoryId)

    override fun getAccountByCategory(categoryIds: List<String>) = accountRepository.getAccountByCategory(categoryIds)

    override fun getAccountByUserId(id: String) = accountRepository.getAccountByUserId(id)
}
