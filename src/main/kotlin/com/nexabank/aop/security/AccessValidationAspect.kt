package com.nexabank.aop.security

import com.nexabank.services.UserAccessService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import org.springframework.security.access.AccessDeniedException

@Aspect
@Component
class AccessValidationAspect(
    private val userAccessService: UserAccessService
) {
    @Before("@annotation(validateAccess)")
    fun validateAccess(joinPoint: JoinPoint, validateAccess: ValidateAccess) {
        val args = joinPoint.args
        val targetUsername = args[0] as String // Assuming the username is the first argument

        val currentUserRole = userAccessService.getCurrentUserRole()
        val currentUsername = userAccessService.getCurrentUsername()

        // Allow admin access even when `checkCurrentUser` is true
        if (currentUserRole == "ROLE_ADMIN") {
            return // Admins can access any endpoint
        }

        // Check if the current user is trying to access their own resources
        if (validateAccess.checkCurrentUser && targetUsername != currentUsername) {
            throw AccessDeniedException("Unauthorized: You can only access your own resources.")
        }

        // Check for required roles
        if (validateAccess.requireRole.isNotEmpty() &&
            currentUserRole !in validateAccess.requireRole
        ) {
            throw AccessDeniedException("Insufficient permissions: Access requires one of the roles ${validateAccess.requireRole}.")
        }
    }
}
