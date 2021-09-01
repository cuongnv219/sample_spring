package com.kaz.passm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PassApplication

fun main(args: Array<String>) {
    runApplication<PassApplication>(*args)
}
