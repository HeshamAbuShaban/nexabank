package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.nexabank.R
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentLoginBinding
import com.nexabank.models.enums.Role
import com.nexabank.repository.AuthenticationRepository
import com.nexabank.ui.MainActivity
import com.nexabank.util.AlarmUtil
import com.nexabank.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val appSharedPreferences: AppSharedPreferences
) :
    ViewModel() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    fun bind(binding: FragmentLoginBinding, navController: NavController) {
        this.binding = binding
        this.navController = navController
    }

    fun register(
        username: String,
        password: String,
        email: String?,
        balance: Double?,
        role: Role,
        onRegisterSuccess: () -> Unit,
        onRegisterFailure: () -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.register(username, password, email, balance, role)
            // Handle result
            if (result.isSuccess) {
                // Registration successful
                AlarmUtil.showSnackBar(
                    binding.root,
                    result.getOrNull() ?: "Registration successful"
                )
                onRegisterSuccess()
            } else {
                // Registration failed
                result.exceptionOrNull()?.printStackTrace()
                AlarmUtil.showSnackBar(
                    binding.root,
                    result.exceptionOrNull()?.message ?: "Registration failed"
                )
                onRegisterFailure()
            }
        }
    }

    fun login(
        username: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: () -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.login(username, password)
            // Handle result
            if (result.isSuccess) {
                // Login successful
                val token = result.getOrNull()
                if (!token.isNullOrEmpty()) {
                    // Save token for future requests
                    TokenManager.saveToken(token)
                    appSharedPreferences.saveUsername(username)
                    AlarmUtil.showSnackBar(binding.root, "Login successful")
                    onLoginSuccess()
                } else {
                    AlarmUtil.showSnackBar(binding.root, "Token is empty")
                    onLoginFailure()
                }
            } else {
                // Login failed
                val exception = result.exceptionOrNull()
                AlarmUtil.showSnackBar(binding.root, "Login failed: ${exception?.message}")
                onLoginFailure()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = repository.logout()
            // Handle result
            if (result.isSuccess) {
                // Logout successful
                TokenManager.removeToken()
                AlarmUtil.showSnackBar(binding.root, result.getOrNull() ?: "Logout successful")
                // Navigate to login screen
                navController.navigate(R.id.login_destination)
                navController.popBackStack()
                // finish the MainActivity to remove it from the back stack and prevent going back to it go to AuthActivity which is currently unAlive
                (binding.root.context as MainActivity).finish()
            } else {
                result.exceptionOrNull()?.printStackTrace()
                AlarmUtil.showSnackBar(
                    binding.root,
                    result.exceptionOrNull()?.message ?: "Logout failed"
                )
            }
        }

    }
}