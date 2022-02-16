package com.github.sumeta.springboot.webflux.h2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringbootRedisTemplateApplication

fun main(args: Array<String>) {
	runApplication<SpringbootRedisTemplateApplication>(*args)
}
