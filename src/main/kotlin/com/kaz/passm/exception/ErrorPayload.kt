package com.kaz.passm.exception

data class ErrorPayload(
    val code: Int,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val errors: List<String>,
    val path: String
) {

    constructor(code: Int, message: String, errors: List<String>, path: String) : this(
        code = code,
        message = message,
        timestamp = System.currentTimeMillis(),
        errors = errors,
        path = path
    )
}
