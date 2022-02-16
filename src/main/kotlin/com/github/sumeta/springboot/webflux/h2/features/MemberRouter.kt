package com.github.sumeta.springboot.webflux.h2.features

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MemberRouter {

    @Bean
    fun registerRoutes(
            memberHandler: MemberHandler
    ) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            "member".nest {
                GET("/get/{id}", memberHandler::get)
                POST("/add", memberHandler::add)
                PATCH("/edit",memberHandler::edit)
                DELETE("/delete/{id}",memberHandler::delete)
            }
        }
    }
}