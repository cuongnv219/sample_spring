package com.kaz.passm.anotation

@Retention(AnnotationRetention.SOURCE)
annotation class CategoryType {
    companion object {
        const val BROWSER = "browser"
        const val APP = "app"
        const val PAYMENT = "payment"
    }
}
