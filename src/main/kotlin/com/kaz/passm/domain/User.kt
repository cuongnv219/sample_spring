package com.kaz.passm.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class User(
    var name: String = "",
    var username: String? = null,
    var email: String = "",
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String = "",
) : AbstractEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    fun mapWithRequest(user: User) {
        name = user.name
        email = user.email
//        password = user.password
    }

//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    var createdAt: Instant? = null
//
//    @CreationTimestamp
//    @Column(name = "updated_at")
//    var updatedAt: Instant? = null
}
