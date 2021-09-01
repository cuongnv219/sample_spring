package com.kaz.passm.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import javax.persistence.*

@Entity
@Table(name = "sub_account")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SubAccount(

    var name: String,
    var password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var account: Account? = null
) : AbstractEntity() {
}
