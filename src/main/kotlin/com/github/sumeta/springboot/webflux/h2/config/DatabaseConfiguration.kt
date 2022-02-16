package com.github.sumeta.springboot.webflux.h2.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator

@Configuration
class DatabaseConfiguration {

    @Bean
    fun initializer(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val popularator = CompositeDatabasePopulator()
        popularator.addPopulators(ResourceDatabasePopulator(ClassPathResource("/db/schema.sql")))
        popularator.addPopulators(ResourceDatabasePopulator(ClassPathResource("/db/data.sql")))
        initializer.setDatabasePopulator(popularator)
        return initializer
    }

}