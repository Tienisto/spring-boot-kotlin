package com.example.demo

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

// https://stackoverflow.com/a/61412233
@Configuration
class FlywayConfig (
        private val env: Environment
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        val url = "jdbc:" + env.getRequiredProperty("db.url")
        val user = env.getRequiredProperty("db.user")
        val password = env.getRequiredProperty("db.password")
        val config = Flyway
                .configure()
                .dataSource(url, user, password)
        return Flyway(config)
    }
}