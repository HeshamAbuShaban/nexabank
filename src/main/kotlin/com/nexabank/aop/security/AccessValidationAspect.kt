package com.nexabank.aop.security

import com.nexabank.services.UserAccessService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class AccessValidationAspect(
    private val userAccessService: UserAccessService
) {
    @Before("@annotation(validateAccess)")
    fun validateAccess(joinPoint: JoinPoint, validateAccess: ValidateAccess) {
        val args = joinPoint.args
        val targetUsername = args[0] as String // Assuming the username is the first argument

        // Validate current user access
        if (validateAccess.checkCurrentUser) {
            userAccessService.validateUserAccess(targetUsername)
        }

        // Validate role if specified
        if (validateAccess.requireRole.isNotBlank()) {
            userAccessService.validateUserAccess(targetUsername, validateAccess.requireRole)
        }
    }
}
