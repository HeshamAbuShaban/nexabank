package com.nexabank.services

import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthorizationService {
    private val logger = LoggerFactory.getLogger(AuthorizationService::class.java)

    fun validateUserAccess(targetUsername: String) {
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUsername = auth.name

        if (targetUsername != loggedInUsername) {
            throw AccessDeniedException("Unauthorized access to the requested resource.")
        }
    }

    fun validateUserAccess(targetUsername: String, requiredRole: String? = null) {
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUsername = auth.name

        if (targetUsername != loggedInUsername) {
            logger.warn("Unauthorized access attempt: User [$loggedInUsername] tried to access [$targetUsername]'s resources.")
            throw AccessDeniedException("Unauthorized access to the requested resource.")
        }

        requiredRole?.let {
            if (auth.authorities.none { it.authority == requiredRole }) {
                logger.warn("Insufficient permissions: User [$loggedInUsername] lacks [$requiredRole] role.")
                throw AccessDeniedException("User lacks the required role: $requiredRole")
            }
        }
    }

}
