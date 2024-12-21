package com.nexabank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NexaBankApplication

fun main(args: Array<String>) {
	runApplication<NexaBankApplication>(*args)
}
