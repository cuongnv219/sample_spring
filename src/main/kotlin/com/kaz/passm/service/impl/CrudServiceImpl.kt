package com.kaz.passm.service.impl

import com.kaz.passm.domain.AbstractEntity
import com.kaz.passm.service.CrudService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.io.Serializable
import java.lang.reflect.InvocationTargetException

@Service
abstract class CrudServiceImpl<TDomain, TId : Serializable> : CrudService<TDomain, TId> {

    protected abstract fun mainRepository(): JpaRepository<TDomain, TId>

    override fun findAll(): List<TDomain>? = mainRepository().findAll()

    override fun findById(id: TId): TDomain? = mainRepository().findById(id).get()

    override fun add(domain: TDomain): TDomain {
        return try {
            val inserted = mainRepository().save(domain ?: throw NullPointerException())!!
//            val id = inserted::class.java.javaClass.getDeclaredMethod("getId").invoke(inserted) as TId
            val id = inserted::class.javaObjectType.getDeclaredMethod("getId").invoke(inserted) as TId
            mainRepository().findById(id) as TDomain
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: NoSuchMethodException) {
            try {
                val id = (domain as? AbstractEntity)?.id as? TId ?: throw NullPointerException("Null")
                mainRepository().findById(id) as TDomain
            } catch (e: Exception) {
                throw NoSuchMethodException()
            }
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }

    override fun update(domain: TDomain): TDomain {
        return add(domain)
    }

    override fun deleteById(id: TId) {
        mainRepository().deleteById(id)
    }
}
