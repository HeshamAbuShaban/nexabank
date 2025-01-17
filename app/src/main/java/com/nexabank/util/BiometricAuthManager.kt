package com.nexabank.util

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nexabank.R

class BiometricAuthManager(
    private val fragment: Fragment,
    private val onAuthSuccess: () -> Unit,
    private val onAuthError: (errorCode: Int, errString: CharSequence) -> Unit,
    private val onAuthFailed: () -> Unit
) {

    private lateinit var biometricPrompt: BiometricPrompt

    private fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(fragment.requireContext())
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    fun authenticate() {
        if (!isBiometricAvailable()) {
            onAuthError.invoke(
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED,
                "Biometric authentication not available or not enrolled."
            )
            return
        }

        val executor = ContextCompat.getMainExecutor(fragment.requireContext())
        biometricPrompt =
            BiometricPrompt(fragment, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onAuthError.invoke(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthSuccess.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onAuthFailed.invoke()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getResString(R.string.biometric_title))
            .setSubtitle(getResString(R.string.biometric_subtitle))
            .setDescription(getResString(R.string.biometric_description))
            .setNegativeButtonText(getResString(R.string.to_be_decide))
            .setConfirmationRequired(true)
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )

        biometricPrompt.authenticate(promptInfo.build())
    }

    private fun getResString(resId: Int): String = fragment.getString(resId)
}