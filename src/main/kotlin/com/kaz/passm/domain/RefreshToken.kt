package com.kaz.passm.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.hibernate.Hibernate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "refresh_token")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RefreshToken(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User? = null,

    @Column(nullable = false, unique = true)
    val token: String = "",

    @Column(nullable = false)
    val expiryDate: Instant = Instant.now()//getters and setters
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RefreshToken

        return id == other.id
    }

    override fun hashCode() = super.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , user = $user , token = $token , expiryDate = $expiryDate )"
    }
}
