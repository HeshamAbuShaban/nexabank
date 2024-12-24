package com.nexabank.aop.security

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidateAccess(val requireRole: String = "", val checkCurrentUser: Boolean = false)
