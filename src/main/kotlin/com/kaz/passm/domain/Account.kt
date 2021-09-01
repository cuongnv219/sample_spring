package com.kaz.passm.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.kaz.passm.anotation.AccountType
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "account")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Account(

    var name: String,
    var username: String,
    @AccountType
    var type: String,
    var password: String,

    @Column(name = "category_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var categoryId: String? = null,

    @Column(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var userId: String? = null,

    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    var subAccount: List<SubAccount>? = listOf()
) : AbstractEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Account

        return id == other.id
    }

    override fun hashCode() = super.hashCode()
}
