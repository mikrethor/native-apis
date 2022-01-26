package com.xavierbouclet.nativeapi

import io.micronaut.core.annotation.TypeHint
import io.micronaut.runtime.Micronaut.build
import java.util.*

@TypeHint(
    value = [
        UUID::class
    ],
    accessType = [TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS]
)
class Application

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.xavierbouclet.nativeapi")
        .start()
}

