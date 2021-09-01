package com.kaz.passm.service

import com.kaz.passm.domain.Category

interface CategoryService : CrudService<Category, String> {

    fun getCategoryByUserId(id: String): List<Category>
}
