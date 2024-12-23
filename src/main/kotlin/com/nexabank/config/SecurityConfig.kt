package com.nexabank.config

import com.nexabank.security.CustomAccessDeniedHandler
import com.nexabank.security.CustomAuthenticationEntryPoint
import com.nexabank.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Configure security policies
        http.csrf { it.disable() } // Disable CSRF for simplicity (not recommended for production)
            .authorizeHttpRequests { requests ->
                // Define public and protected endpoints
                requests
                    .requestMatchers("/api/auth/**", "/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                    ).permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only endpoints
                    .anyRequest().authenticated() // Protected: Requires authentication
            }
            .exceptionHandling { it
                .authenticationEntryPoint(CustomAuthenticationEntryPoint()) // Customize error handling
                .accessDeniedHandler(CustomAccessDeniedHandler())
            }
            .addFilterBefore(
                JwtAuthenticationFilter(userDetailsService), // Add JWT filter
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
