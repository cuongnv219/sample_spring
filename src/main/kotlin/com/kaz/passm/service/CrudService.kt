package com.kaz.passm.service

interface CrudService<T, ID> {
    fun findAll(): List<T>?
    fun findById(id: ID): T?
    fun add(domain: T): T
    fun update(domain: T): T
    fun deleteById(id: ID)
}
