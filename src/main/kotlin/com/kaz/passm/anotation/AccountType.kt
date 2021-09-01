package com.kaz.passm.anotation

//@StringDef(BANK, OTHER)
@Retention(AnnotationRetention.SOURCE)
annotation class AccountType {
    companion object {
        const val BANK = "bank"
        const val OTHER = "other"
    }
}
