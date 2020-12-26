package com.lafabrique.digit.owl

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.lafabrique.digit.owl")
		.start()
}

