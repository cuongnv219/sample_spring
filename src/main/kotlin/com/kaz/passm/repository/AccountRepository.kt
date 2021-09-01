package com.kaz.passm.repository

import com.kaz.passm.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, String> {

    @Query(value = "select * from account where category_id =:categoryId", nativeQuery = true)
    fun getAccountByCategory(categoryId: String): List<Account>

    @Query(value = "select * from account where category_id in(:categoryIds)", nativeQuery = true)
    fun getAccountByCategory(categoryIds: List<String>): List<Account>

        @Query(value = "select * from account where user_id =:id", nativeQuery = true)
//    @Query("FROM account AS rdt LEFT JOIN rdt.cacheMedias AS cm WHERE cm.id = id")    //This is using a named query method
    fun getAccountByUserId(id: String): List<Account>
}
