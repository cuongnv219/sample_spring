package com.kaz.passm.repository

import com.kaz.passm.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, String> {

    @Query(value = "select * from category where user_id =:id", nativeQuery = true)
    fun getCategoryByUserId(id: String): List<Category>
}
