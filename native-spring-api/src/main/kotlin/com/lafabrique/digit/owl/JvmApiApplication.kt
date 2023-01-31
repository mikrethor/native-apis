package com.lafabrique.digit.owl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration(proxyBeanMethods = false)
class JvmApiApplication

fun main(args: Array<String>) {
    runApplication<JvmApiApplication>(*args)
}
