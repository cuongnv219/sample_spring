package com.kaz.passm.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.kaz.passm.anotation.CategoryType
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "category")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Category(

    var name: String = "",

    @CategoryType
    var type: String = "",

//    @ManyToOne
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @JoinColumn(name = "user_id", referencedColumnName = "client_id")
//    var userId: User? = null,

    @Column(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var userId: String? = null,
) : AbstractEntity() {

    fun mapWithRequest(category: Category) {
        name = category.name
        type = category.type
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Category

        return id == other.id
    }

    override fun hashCode(): Int = 1596826009

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name , type = $type , userId = $userId )"
    }
}
