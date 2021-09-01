package com.kaz.passm.domain

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant
import java.util.UUID
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonPropertyOrder("id")
abstract class AbstractEntity : Serializable {

    @Id
    val id = UUID.randomUUID().toString()

    override fun hashCode() = id.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (other !is AbstractEntity) {
            return false
        }
        return id == other.id
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null

    @CreationTimestamp
    @Column(name = "updated_at")
    var updatedAt: Instant? = null
}
