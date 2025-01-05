package com.nexabank.util

class InputValidator {

    fun isUsernameValid(username: String): Boolean {
        return username.isNotEmpty()
    }

    data class PasswordValidationResult(val isValid: Boolean, val errorMessage: String? = null)

    fun isPasswordValid(password: String): PasswordValidationResult {
        // Complex password checks:
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val isLongEnough = password.length >= 8 // Minimum length

        if (!isLongEnough) {
            return PasswordValidationResult(false, "Password must be at least 8 characters long")
        }
        if (!hasUpperCase) {
            return PasswordValidationResult(
                false,
                "Password must contain at least one uppercase letter"
            )
        }
        if (!hasLowerCase) {
            return PasswordValidationResult(
                false,
                "Password must contain at least one lowercase letter"
            )
        }
        if (!hasDigit) {
            return PasswordValidationResult(false, "Password must contain at least one digit")
        }
        if (!hasSpecialChar) {
            return PasswordValidationResult(
                false,
                "Password must contain at least one special character"
            )
        }

        return PasswordValidationResult(true) // Password is valid
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isBalanceValid(balance: String?): Boolean {
        return balance?.toDoubleOrNull()?.let { it >= 0.0 } ?: false
    }
}