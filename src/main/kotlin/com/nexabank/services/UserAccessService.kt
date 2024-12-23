package com.nexabank.services

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserAccessService {
    fun validateUserAccess(targetUsername: String, requiredRole: String? = null) {
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUsername = auth.name

        if (targetUsername != loggedInUsername) {
            throw AccessDeniedException("Unauthorized access to the requested resource.")
        }

        requiredRole?.let {
            if (auth.authorities.none { it.authority == requiredRole }) {
                throw AccessDeniedException("User lacks the required role: $requiredRole")
            }
        }
    }
}
