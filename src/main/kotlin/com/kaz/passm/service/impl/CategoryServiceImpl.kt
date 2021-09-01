package com.kaz.passm.service.impl

import com.kaz.passm.domain.Category
import com.kaz.passm.repository.CategoryRepository
import com.kaz.passm.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl : CrudServiceImpl<Category, String>(), CategoryService {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    override fun mainRepository() = categoryRepository

    override fun getCategoryByUserId(id: String) = categoryRepository.getCategoryByUserId(id)
}
