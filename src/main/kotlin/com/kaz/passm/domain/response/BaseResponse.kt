package com.kaz.passm.domain.response

data class BaseResponse(
    val data: Any? = null,
    val success: Boolean = true,
) {
}
