package com.example.demo.config

import com.example.demo.model.Role
import com.example.demo.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * This class sets all security related configuration.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenService: TokenService,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Define public and private routes
        http.authorizeHttpRequests { authorizeHttpRequests ->
            authorizeHttpRequests
                //Authentication APIs
                .requestMatchers(HttpMethod.POST,   "/api/auth/*/login").permitAll()
                .requestMatchers(HttpMethod.POST,   "/api/auth/*/register").permitAll()
                .requestMatchers(HttpMethod.PUT,    "/api/auth/*/changeRole").hasAuthority(Role.ADMIN.name)
                //Items APIs
                .requestMatchers(HttpMethod.GET,    "/api/items/*/getAll").hasAuthority(Role.ADMIN.name)
                .requestMatchers(HttpMethod.GET,    "/api/items/*/getAllByUser").hasAnyAuthority(Role.VIEWER.name, Role.COLLECTOR.name, Role.EDITOR.name, Role.MANAGER.name, Role.ADMIN.name)
                .requestMatchers(HttpMethod.POST,   "/api/items/*/*").hasAnyAuthority(Role.COLLECTOR.name, Role.EDITOR.name, Role.MANAGER.name, Role.ADMIN.name)
                .requestMatchers(HttpMethod.PUT,    "/api/items/*/*").hasAnyAuthority(Role.EDITOR.name, Role.MANAGER.name, Role.ADMIN.name)
                .requestMatchers(HttpMethod.DELETE, "/api/items/*/*").hasAnyAuthority(Role.MANAGER.name, Role.ADMIN.name)
                //ALL APIs
                .anyRequest().authenticated()
        }.oauth2ResourceServer { oauth2ResourceServer ->
            oauth2ResourceServer.jwt{ jwt ->
                jwt
            }
        }.authenticationManager { authenticationManager ->
            val jwt = authenticationManager as BearerTokenAuthenticationToken
            val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
            UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority(user.role.name)))
        }.csrf { csrf ->
            csrf.disable()
        }.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.headers { headers ->
            headers.frameOptions { frameOptions ->
                frameOptions.disable()
            }.xssProtection { xssProtection ->
                xssProtection.disable()
            }
        }.cors{ cors ->
            cors

        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type", "Host")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}
