package com.nexabank.services

import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserAccessService {
    private val logger = LoggerFactory.getLogger(UserAccessService::class.java)

    fun getCurrentUsername(): String {
        val auth = SecurityContextHolder.getContext().authentication
        return auth.name
    }

    fun getCurrentUserRole(): String {
        val auth = SecurityContextHolder.getContext().authentication
        return auth.authorities.firstOrNull()?.authority
            ?: throw AccessDeniedException("No role associated with the current user.")
    }

    fun validateUserAccess(targetUsername: String, requiredRoles: List<String> = emptyList()) {
        val currentUsername = getCurrentUsername()
        val currentUserRole = getCurrentUserRole()

        // Check if the user is accessing their own resources
        if (targetUsername != currentUsername && currentUserRole != "ROLE_ADMIN") {
            logger.warn("Unauthorized access attempt: User [$currentUsername] tried to access [$targetUsername]'s resources.")
            throw AccessDeniedException("Unauthorized access to the requested resource.")
        }

        // Check for required roles
        if (requiredRoles.isNotEmpty() && currentUserRole !in requiredRoles) {
            logger.warn("Insufficient permissions: User [$currentUsername] lacks the required roles: $requiredRoles.")
            throw AccessDeniedException("User lacks the required roles: $requiredRoles")
        }
    }
}
