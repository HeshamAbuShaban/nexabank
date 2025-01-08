package com.nexabank.aop.security

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidateAccess(val requireRole: Array<String> = ["ROLE_USER"], val checkCurrentUser: Boolean = false)
